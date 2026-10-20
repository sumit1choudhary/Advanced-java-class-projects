package com.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String name = safe(request.getParameter("name"));
        String email = safe(request.getParameter("email"));
        String course = safe(request.getParameter("course"));
        response.getWriter().println("<!doctype html><html lang='en'><head><meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1'><title>Student Details</title><link rel='stylesheet' href='style.css'></head><body><main class='card'><p class='eyebrow'>FORM SUBMITTED</p><h1>Student Information</h1><p><strong>Name:</strong> " + escapeHtml(name)
                + "</p><p><strong>Email:</strong> " + escapeHtml(email) + "</p><p><strong>Course:</strong> " + escapeHtml(course)
                + "</p><a href='home'>Back to home</a></main></body></html>");
    }
    private String safe(String value) { return value == null ? "" : value.trim(); }
    private String escapeHtml(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#x27;");
    }
}
