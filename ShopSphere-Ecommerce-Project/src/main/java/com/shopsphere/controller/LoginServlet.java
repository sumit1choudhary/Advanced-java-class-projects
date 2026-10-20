
package com.shopsphere.controller;

import com.shopsphere.service.AuthService;
import com.shopsphere.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest r, HttpServletResponse s)
            throws IOException, ServletException {

        try {
            User u = new AuthService().login(
                    r.getParameter("email"),
                    r.getParameter("password")
            );

            if (u == null) {
                r.setAttribute("error", "Invalid email or password");
                r.getRequestDispatcher("/login.jsp").forward(r, s);
                return;
            }

            // Prevent customers from using the admin account flow
            if ("ADMIN".equalsIgnoreCase(u.getRole())) {
                r.setAttribute("error",
                        "Please use the Admin Login page.");
                r.getRequestDispatcher("/login.jsp").forward(r, s);
                return;
            }

            HttpSession session = r.getSession();
            session.setAttribute("user", u);
            session.setAttribute("role", u.getRole());

            s.sendRedirect(r.getContextPath() + "/products");

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
