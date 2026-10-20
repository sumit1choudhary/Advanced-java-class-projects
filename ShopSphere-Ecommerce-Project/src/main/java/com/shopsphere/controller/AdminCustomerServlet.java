 
package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.util.DB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/admin/customers")
public class AdminCustomerServlet extends HttpServlet {

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

        List<Map<String, Object>> customers = new ArrayList<>();

        String sql = "SELECT user_id, name, email, mobile, role " +
                     "FROM users ORDER BY user_id DESC";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> customer = new LinkedHashMap<>();
                customer.put("id", rs.getInt("user_id"));
                customer.put("name", rs.getString("name"));
                customer.put("email", rs.getString("email"));
                customer.put("mobile", rs.getString("mobile"));
                customer.put("role", rs.getString("role"));
                customers.add(customer);
            }

            req.setAttribute("customers", customers);
            req.getRequestDispatcher("/admin/customers.jsp")
               .forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Unable to load customers.", e);
        }
    }
}

