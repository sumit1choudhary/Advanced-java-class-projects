
package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.util.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/my-orders")
public class OrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                !(session.getAttribute("user") instanceof User)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        List<Map<String, Object>> orders = new ArrayList<>();

        String orderSql =
            "SELECT order_id, created_at, total_amount, " +
            "order_status, payment_status " +
            "FROM orders WHERE user_id = ? " +
            "ORDER BY created_at DESC";

        String itemsSql =
            "SELECT oi.product_id, oi.quantity, oi.price, " +
            "p.name, p.image_url " +
            "FROM order_items oi " +
            "LEFT JOIN products p ON oi.product_id = p.product_id " +
            "WHERE oi.order_id = ?";

        try (Connection con = DB.getConnection();
             PreparedStatement orderPs = con.prepareStatement(orderSql)) {

            orderPs.setInt(1, user.getUserId());

            try (ResultSet rs = orderPs.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> order = new HashMap<>();

                    int orderId = rs.getInt("order_id");

                    order.put("orderId", orderId);
                    order.put("createdAt", rs.getTimestamp("created_at"));
                    order.put("total", rs.getBigDecimal("total_amount"));
                    order.put("status", rs.getString("order_status"));
                    order.put("paymentStatus",
                              rs.getString("payment_status"));

                    List<Map<String, Object>> items = new ArrayList<>();

                    try (PreparedStatement itemPs =
                             con.prepareStatement(itemsSql)) {

                        itemPs.setInt(1, orderId);

                        try (ResultSet itemRs = itemPs.executeQuery()) {
                            while (itemRs.next()) {
                                Map<String, Object> item = new HashMap<>();

                                item.put("productId",
                                         itemRs.getInt("product_id"));
                                item.put("name",
                                         itemRs.getString("name"));
                                item.put("image",
                                         itemRs.getString("image_url"));
                                item.put("quantity",
                                         itemRs.getInt("quantity"));
                                item.put("price",
                                         itemRs.getBigDecimal("price"));

                                items.add(item);
                            }
                        }
                    }

                    order.put("items", items);
                    orders.add(order);
                }
            }

            request.setAttribute("orders", orders);

            request.getRequestDispatcher("/my-orders.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Unable to load order details.", e);
        }
    }
}
