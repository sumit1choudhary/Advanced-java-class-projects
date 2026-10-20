package com.shopsphere.controller;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest r, HttpServletResponse s) throws IOException {
        r.getSession().invalidate();
        s.sendRedirect("index.jsp");
    }
}
