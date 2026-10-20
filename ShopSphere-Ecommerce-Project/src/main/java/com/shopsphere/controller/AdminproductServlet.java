 
package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.util.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/admin/products")
public class AdminproductServlet extends HttpServlet {

    private boolean isAdmin(HttpServletRequest req,
                            HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null ||
            !(session.getAttribute("user") instanceof User)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return false;
        }
        User user = (User) session.getAttribute("user");
        if (user.getRole() == null ||
            !"ADMIN".equalsIgnoreCase(user.getRole().trim())) {
            resp.sendError(403, "Admin access required.");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdmin(req, resp)) return;

        String editId = req.getParameter("edit");

        try (Connection con = DB.getConnection()) {
            List<Map<String, Object>> products = new ArrayList<>();

            String sql = "SELECT p.*, c.name AS category_name " +
                         "FROM products p LEFT JOIN categories c " +
                         "ON p.category_id = c.category_id " +
                         "ORDER BY p.product_id DESC";

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> p = new LinkedHashMap<>();
                    p.put("productId", rs.getInt("product_id"));
                    p.put("categoryId", rs.getInt("category_id"));
                    p.put("name", rs.getString("name"));
                    p.put("brand", rs.getString("brand"));
                    p.put("description", rs.getString("description"));
                    p.put("price", rs.getBigDecimal("price"));
                    p.put("discount", rs.getBigDecimal("discount"));
                    p.put("stock", rs.getInt("stock"));
                    p.put("imageUrl", rs.getString("image_url"));
                    p.put("status", rs.getInt("status"));
                    p.put("categoryName", rs.getString("category_name"));
                    products.add(p);
                }
            }

            List<Map<String, Object>> categories = new ArrayList<>();
            try (PreparedStatement ps = con.prepareStatement(
                    "SELECT category_id, name FROM categories WHERE status = 1 ORDER BY name");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> c = new HashMap<>();
                    c.put("id", rs.getInt("category_id"));
                    c.put("name", rs.getString("name"));
                    categories.add(c);
                }
            }

            req.setAttribute("products", products);
            req.setAttribute("categories", categories);

            if (editId != null && !editId.trim().isEmpty()) {
                int id = Integer.parseInt(editId);
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM products WHERE product_id = ?")) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            Map<String, Object> p = new HashMap<>();
                            p.put("productId", rs.getInt("product_id"));
                            p.put("categoryId", rs.getInt("category_id"));
                            p.put("name", rs.getString("name"));
                            p.put("brand", rs.getString("brand"));
                            p.put("description", rs.getString("description"));
                            p.put("price", rs.getBigDecimal("price"));
                            p.put("discount", rs.getBigDecimal("discount"));
                            p.put("stock", rs.getInt("stock"));
                            p.put("imageUrl", rs.getString("image_url"));
                            p.put("status", rs.getInt("status"));
                            req.setAttribute("editProduct", p);
                        }
                    }
                }
            }

            req.getRequestDispatcher("/admin/products.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Unable to load products.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdmin(req, resp)) return;
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");

        try (Connection con = DB.getConnection()) {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("productId"));

                // Soft-delete keeps historical order references intact.
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE products SET status = 0 WHERE product_id = ?")) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

            } else if ("save".equals(action)) {
                String idText = req.getParameter("productId");
                int categoryId = Integer.parseInt(req.getParameter("categoryId"));
                String name = req.getParameter("name").trim();
                String brand = req.getParameter("brand");
                String description = req.getParameter("description");
                double price = Double.parseDouble(req.getParameter("price"));
                double discount = Double.parseDouble(req.getParameter("discount"));
                int stock = Integer.parseInt(req.getParameter("stock"));
                String imageUrl = req.getParameter("imageUrl");
                int status = Integer.parseInt(req.getParameter("status"));

                if (name.isEmpty() || price < 0 || stock < 0 ||
                    discount < 0 || discount > 100 ||
                    (status != 0 && status != 1)) {
                    resp.sendError(400, "Please enter valid product details.");
                    return;
                }

                if (idText == null || idText.trim().isEmpty()) {
                    String sql = "INSERT INTO products " +
                        "(category_id, name, brand, description, price, discount, stock, image_url, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, categoryId);
                        ps.setString(2, name);
                        ps.setString(3, brand);
                        ps.setString(4, description);
                        ps.setDouble(5, price);
                        ps.setDouble(6, discount);
                        ps.setInt(7, stock);
                        ps.setString(8, imageUrl);
                        ps.setInt(9, status);
                        ps.executeUpdate();
                    }
                } else {
                    int id = Integer.parseInt(idText);
                    String sql = "UPDATE products SET category_id=?, name=?, brand=?, " +
                        "description=?, price=?, discount=?, stock=?, image_url=?, status=? " +
                        "WHERE product_id=?";

                    try (PreparedStatement ps = con.prepareStatement(sql)) {
                        ps.setInt(1, categoryId);
                        ps.setString(2, name);
                        ps.setString(3, brand);
                        ps.setString(4, description);
                        ps.setDouble(5, price);
                        ps.setDouble(6, discount);
                        ps.setInt(7, stock);
                        ps.setString(8, imageUrl);
                        ps.setInt(9, status);
                        ps.setInt(10, id);
                        ps.executeUpdate();
                    }
                }
            } else {
                resp.sendError(400, "Unknown action.");
                return;
            }

            resp.sendRedirect(req.getContextPath() + "/admin/products");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Unable to save product.", e);
        }
    }
}
