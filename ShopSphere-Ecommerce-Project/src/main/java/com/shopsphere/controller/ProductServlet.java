package com.shopsphere.controller;

import com.shopsphere.service.ProductService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            int categoryId = parseInt(request.getParameter("category"), 0);
            double minPrice = parseDouble(request.getParameter("minPrice"), 0);
            double maxPrice = parseDouble(request.getParameter("maxPrice"), 1_000_000);
            String sort = request.getParameter("sort");

            if (categoryId < 0) categoryId = 0;
            if (minPrice < 0 || maxPrice < minPrice) {
                minPrice = 0;
                maxPrice = 1_000_000;
                request.setAttribute("filterError", "Please enter a valid price range.");
            }

            request.setAttribute("products", new ProductService().list(
                request.getParameter("q"), categoryId, minPrice, maxPrice, sort
            ));
            request.getRequestDispatcher("/products.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Unable to load products", e);
        }
    }

    private int parseInt(String value, int fallback) {
        try {
            return value == null || value.isBlank() ? fallback : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private double parseDouble(String value, double fallback) {
        try {
            double number = value == null || value.isBlank()
                ? fallback : Double.parseDouble(value);
            return Double.isFinite(number) ? number : fallback;
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
