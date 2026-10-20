 
package com.shopsphere.controller;

import com.shopsphere.service.AuthService;
import com.shopsphere.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest r, HttpServletResponse s)
            throws IOException, ServletException {

        r.getRequestDispatcher("/admin-login.jsp").forward(r, s);
    }

    @Override
    protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws IOException, ServletException {

        try {
            User u = new AuthService().login(
                    r.getParameter("email"),
                    r.getParameter("password")
            );

            if (u == null || !"ADMIN".equalsIgnoreCase(u.getRole())) {
                r.setAttribute("error",
                        "Invalid admin email or password.");
                r.getRequestDispatcher("/admin-login.jsp").forward(r, s);
                return;
            }

            // Create a fresh session after successful authentication
            HttpSession oldSession = r.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession session = r.getSession(true);
            session.setAttribute("user", u);
            session.setAttribute("role", u.getRole());

            s.sendRedirect(r.getContextPath() + "/admin/dashboard");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
