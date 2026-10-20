package com.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }
        String username = String.valueOf(session.getAttribute("username"));
        response.getWriter().println("<!doctype html><html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1'><title>Student Home</title><link rel='stylesheet' href='style.css'></head><body><main class='card'><p class='eyebrow'>JAKARTA SERVLET PRACTICAL</p><h1>Student Home</h1><p>Welcome, <strong>"
                + escapeHtml(username) + "</strong>!</p><p>You have successfully logged in. This page is protected by <code>AuthFilter</code>.</p><div class='actions'><a class='button' href='student.html'>Student form</a><a class='button secondary' href='hello?name=" + username + "'>Hello servlet</a><a class='button danger' href='logout'>Logout</a></div></main></body></html>");
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#x27;");
    }
}
