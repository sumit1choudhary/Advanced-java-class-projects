 
package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.util.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Protect the dashboard.
        HttpSession session = request.getSession(false);

        if (session == null ||
                !(session.getAttribute("user") instanceof User)) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        User user = (User) session.getAttribute("user");
        String role = user.getRole();

        if (role == null || !"ADMIN".equalsIgnoreCase(role.trim())) {
            response.sendError(
                HttpServletResponse.SC_FORBIDDEN,
                "Admin access required."
            );
            return;
        }

        String statsSql =
            "SELECT " +
            "(SELECT COUNT(*) FROM products WHERE status = 1) AS total_products, " +
            "(SELECT COUNT(*) FROM orders) AS total_orders, " +
            "(SELECT COUNT(*) FROM users) AS total_customers, " +
            "(SELECT COALESCE(SUM(total_amount), 0) FROM orders " +
            " WHERE order_status IS NULL OR UPPER(order_status) <> 'CANCELLED') " +
            "AS total_revenue";

        String recentOrdersSql =
            "SELECT o.order_id, o.created_at, o.total_amount, " +
            "o.order_status, o.payment_status, u.name AS customer_name " +
            "FROM orders o " +
            "LEFT JOIN users u ON o.user_id = u.user_id " +
            "ORDER BY o.created_at DESC, o.order_id DESC " +
            "LIMIT 8";

        List<Map<String, Object>> recentOrders = new ArrayList<>();

        try (Connection con = DB.getConnection()) {

            // Load summary statistics.
            try (PreparedStatement ps =
                     con.prepareStatement(statsSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    request.setAttribute(
                        "totalProducts", rs.getLong("total_products")
                    );
                    request.setAttribute(
                        "totalOrders", rs.getLong("total_orders")
                    );
                    request.setAttribute(
                        "totalCustomers", rs.getLong("total_customers")
                    );

                    BigDecimal revenue = rs.getBigDecimal("total_revenue");
                    request.setAttribute(
                        "totalRevenue",
                        revenue != null ? revenue : BigDecimal.ZERO
                    );
                }
            }

            // Load the latest orders.
            try (PreparedStatement ps =
                     con.prepareStatement(recentOrdersSql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> order = new LinkedHashMap<>();

                    order.put("orderId", rs.getInt("order_id"));
                    order.put("customerName", rs.getString("customer_name"));
                    order.put("createdAt", rs.getTimestamp("created_at"));
                    order.put("total", rs.getBigDecimal("total_amount"));
                    order.put("status", rs.getString("order_status"));
                    order.put("paymentStatus", rs.getString("payment_status"));

                    recentOrders.add(order);
                }
            }

            request.setAttribute("recentOrders", recentOrders);

            request.getRequestDispatcher("/admin/dashboard.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                "Unable to load admin dashboard from the database.", e
            );
        }
    }
}

