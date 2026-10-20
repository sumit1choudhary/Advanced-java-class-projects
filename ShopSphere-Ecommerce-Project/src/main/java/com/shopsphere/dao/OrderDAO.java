
package com.shopsphere.dao;

import com.shopsphere.model.CartItem;
import com.shopsphere.util.DB;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class OrderDAO {

    public int placeOrder(
            int userId,
            String addressLine,
            String city,
            String state,
            String pincode,
            String paymentMethod,
            Map<Integer, CartItem> cart) throws SQLException {

        if (cart == null || cart.isEmpty()) {
            throw new SQLException("Your cart is empty.");
        }

        if (addressLine == null || addressLine.trim().isEmpty()
                || city == null || city.trim().isEmpty()
                || pincode == null || !pincode.trim().matches("\\d{6}")) {
            throw new SQLException("Enter a valid delivery address and 6-digit pincode.");
        }

        if (!"Cash on Delivery".equals(paymentMethod)
                && !"Mock UPI".equals(paymentMethod)
                && !"Mock Card".equals(paymentMethod)) {
            throw new SQLException("Invalid payment method.");
        }

        try (Connection con = DB.getConnection()) {
            con.setAutoCommit(false);

            try {
                // 1. Save delivery address
                int addressId;

                String addressSql =
                    "INSERT INTO addresses " +
                    "(user_id, address_line, city, state, pincode, address_type) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = con.prepareStatement(
                        addressSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, userId);
                    ps.setString(2, addressLine.trim());
                    ps.setString(3, city.trim());
                    ps.setString(4, state == null ? "" : state.trim());
                    ps.setString(5, pincode.trim());
                    ps.setString(6, "DELIVERY");
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("Could not save delivery address.");
                        }
                        addressId = keys.getInt(1);
                    }
                }

                // 2. Lock products and calculate prices from the database.
                BigDecimal total = BigDecimal.ZERO;

                for (Map.Entry<Integer, CartItem> entry : cart.entrySet()) {
                    int productId = entry.getKey();
                    int quantity = entry.getValue().getQuantity();

                    if (quantity <= 0) {
                        throw new SQLException("Invalid product quantity.");
                    }

                    try (PreparedStatement ps = con.prepareStatement(
                            "SELECT price, discount, stock, status " +
                            "FROM products WHERE product_id = ? FOR UPDATE")) {
                        ps.setInt(1, productId);

                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next() || rs.getInt("status") != 1) {
                                throw new SQLException("A product is unavailable.");
                            }

                            if (rs.getInt("stock") < quantity) {
                                throw new SQLException(
                                    "Insufficient stock for product ID " + productId);
                            }

                            BigDecimal price = rs.getBigDecimal("price");
                            BigDecimal discount = rs.getBigDecimal("discount");

                            if (discount == null) {
                                discount = BigDecimal.ZERO;
                            }

                            BigDecimal finalPrice = price
                                .multiply(BigDecimal.ONE.subtract(
                                    discount.divide(new BigDecimal("100"), 6,
                                        java.math.RoundingMode.HALF_UP)))
                                .setScale(2, java.math.RoundingMode.HALF_UP);

                            total = total.add(
                                finalPrice.multiply(BigDecimal.valueOf(quantity)));
                        }
                    }
                }

                // 3. Create order
                int orderId;

                String orderSql =
                    "INSERT INTO orders " +
                    "(user_id, address_id, total_amount, payment_method, " +
                    "payment_status, order_status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

                String paymentStatus = "PENDING";

                try (PreparedStatement ps = con.prepareStatement(
                        orderSql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, addressId);
                    ps.setBigDecimal(3, total);
                    ps.setString(4, paymentMethod);
                    ps.setString(5, paymentStatus);
                    ps.setString(6, "PLACED");
                    ps.executeUpdate();

                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new SQLException("Could not create order.");
                        }
                        orderId = keys.getInt(1);
                    }
                }

                // 4. Save order items and reduce stock
                String itemSql =
                    "INSERT INTO order_items (order_id, product_id, quantity, price) " +
                    "VALUES (?, ?, ?, ?)";

                String stockSql =
                    "UPDATE products SET stock = stock - ? " +
                    "WHERE product_id = ? AND stock >= ? AND status = 1";

                for (Map.Entry<Integer, CartItem> entry : cart.entrySet()) {
                    int productId = entry.getKey();
                    int quantity = entry.getValue().getQuantity();
                    BigDecimal finalPrice;

                    try (PreparedStatement ps = con.prepareStatement(
                            "SELECT price, discount FROM products WHERE product_id = ?")) {
                        ps.setInt(1, productId);

                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) {
                                throw new SQLException("Product not found.");
                            }

                            BigDecimal price = rs.getBigDecimal("price");
                            BigDecimal discount = rs.getBigDecimal("discount");

                            if (discount == null) {
                                discount = BigDecimal.ZERO;
                            }

                            finalPrice = price.multiply(
                                BigDecimal.ONE.subtract(discount.divide(
                                    new BigDecimal("100"), 6,
                                    java.math.RoundingMode.HALF_UP)))
                                .setScale(2, java.math.RoundingMode.HALF_UP);
                        }
                    }

                    try (PreparedStatement ps = con.prepareStatement(itemSql)) {
                        ps.setInt(1, orderId);
                        ps.setInt(2, productId);
                        ps.setInt(3, quantity);
                        ps.setBigDecimal(4, finalPrice);
                        ps.executeUpdate();
                    }

                    try (PreparedStatement ps = con.prepareStatement(stockSql)) {
                        ps.setInt(1, quantity);
                        ps.setInt(2, productId);
                        ps.setInt(3, quantity);

                        if (ps.executeUpdate() != 1) {
                            throw new SQLException("Stock changed. Please retry checkout.");
                        }
                    }
                }

                // 5. Record demo/COD payment as pending, not paid.
                String reference = "DEMO-" + UUID.randomUUID();

                String paymentSql =
                    "INSERT INTO payments " +
                    "(order_id, payment_method, transaction_reference, amount, payment_status) " +
                    "VALUES (?, ?, ?, ?, ?)";

                try (PreparedStatement ps = con.prepareStatement(paymentSql)) {
                    ps.setInt(1, orderId);
                    ps.setString(2, paymentMethod);
                    ps.setString(3, reference);
                    ps.setBigDecimal(4, total);
                    ps.setString(5, paymentStatus);
                    ps.executeUpdate();
                }

                con.commit();
                return orderId;

            } catch (Exception e) {
                con.rollback();

                if (e instanceof SQLException) {
                    throw (SQLException) e;
                }
                throw new SQLException("Could not place order.", e);

            } finally {
                con.setAutoCommit(true);
            }
        }
    }
}
