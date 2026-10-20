 

package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.util.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {

    private boolean isAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || !(s.getAttribute("user") instanceof User)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return false;
        }
        User u = (User) s.getAttribute("user");
        if (u.getRole() == null || !"ADMIN".equalsIgnoreCase(u.getRole().trim())) {
            resp.sendError(403, "Admin access required.");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdmin(req, resp)) return;

        List<Map<String, Object>> orders = new ArrayList<>();
        String sql = "SELECT o.order_id, o.user_id, o.created_at, " +
            "o.total_amount, o.order_status, o.payment_status, " +
            "o.payment_method, u.name AS customer_name, u.email " +
            "FROM orders o LEFT JOIN users u ON o.user_id=u.user_id " +
            "ORDER BY o.created_at DESC, o.order_id DESC";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> o = new LinkedHashMap<>();
                o.put("id", rs.getInt("order_id"));
                o.put("userId", rs.getInt("user_id"));
                o.put("customer", rs.getString("customer_name"));
                o.put("email", rs.getString("email"));
                o.put("date", rs.getTimestamp("created_at"));
                o.put("total", rs.getBigDecimal("total_amount"));
                o.put("status", rs.getString("order_status"));
                o.put("paymentStatus", rs.getString("payment_status"));
                o.put("paymentMethod", rs.getString("payment_method"));
                orders.add(o);
            }
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to load orders.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!isAdmin(req, resp)) return;

        int id;
        String status = req.getParameter("status");
        List<String> allowed = Arrays.asList(
            "PLACED", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED"
        );

        try {
            id = Integer.parseInt(req.getParameter("orderId"));
        } catch (Exception e) {
            resp.sendError(400, "Invalid order ID.");
            return;
        }

        if (status == null || !allowed.contains(status)) {
            resp.sendError(400, "Invalid order status.");
            return;
        }

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "UPDATE orders SET order_status=? WHERE order_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
        } catch (SQLException e) {
            throw new ServletException("Unable to update order status.", e);
        }
    }
}
