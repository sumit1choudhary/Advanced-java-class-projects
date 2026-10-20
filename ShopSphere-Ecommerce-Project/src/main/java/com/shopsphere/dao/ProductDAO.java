package com.shopsphere.dao;

import com.shopsphere.model.Product;
import com.shopsphere.util.DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Product map(ResultSet r) throws SQLException {
        Product p = new Product();
        p.setProductId(r.getInt("product_id"));
        p.setCategoryId(r.getInt("category_id"));
        p.setName(r.getString("name"));
        p.setBrand(r.getString("brand"));
        p.setDescription(r.getString("description"));
        p.setPrice(r.getDouble("price"));
        p.setDiscount(r.getDouble("discount"));
        p.setStock(r.getInt("stock"));
        p.setImageUrl(r.getString("image_url"));
        return p;
    }

    public List<Product> all(String search) throws SQLException {
        return all(search, 0, 0, 1_000_000, "");
    }

    public List<Product> all(String search, int categoryId,
                             double minPrice, double maxPrice,
                             String sort) throws SQLException {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT * FROM products WHERE status = 1"
        );

        boolean hasSearch = search != null && !search.trim().isEmpty();
        if (hasSearch) {
            sql.append(" AND (name LIKE ? OR brand LIKE ?)");
        }
        if (categoryId > 0) {
            sql.append(" AND category_id = ?");
        }
        sql.append(" AND (price * (1 - COALESCE(discount, 0) / 100.0)) >= ?");
        sql.append(" AND (price * (1 - COALESCE(discount, 0) / 100.0)) <= ?");

        if ("price_asc".equals(sort)) {
            sql.append(" ORDER BY (price * (1 - COALESCE(discount, 0) / 100.0)) ASC");
        } else if ("price_desc".equals(sort)) {
            sql.append(" ORDER BY (price * (1 - COALESCE(discount, 0) / 100.0)) DESC");
        } else {
            sql.append(" ORDER BY product_id DESC");
        }

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int i = 1;
            if (hasSearch) {
                String keyword = "%" + search.trim() + "%";
                ps.setString(i++, keyword);
                ps.setString(i++, keyword);
            }
            if (categoryId > 0) {
                ps.setInt(i++, categoryId);
            }
            ps.setDouble(i++, minPrice);
            ps.setDouble(i, maxPrice);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    products.add(map(rs));
                }
            }
        }
        return products;
    }

    public Product find(int id) throws SQLException {
        try (Connection c = DB.getConnection();
             PreparedStatement p = c.prepareStatement(
                 "SELECT * FROM products WHERE product_id = ? AND status = 1")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                return r.next() ? map(r) : null;
            }
        }
    }
}
