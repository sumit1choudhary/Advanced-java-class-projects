package com.shopsphere.controller;

import com.shopsphere.model.*;
import com.shopsphere.service.ProductService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    protected void doGet(HttpServletRequest r, HttpServletResponse s) throws IOException, ServletException {
        r.getRequestDispatcher("/cart.jsp").forward(r, s);
    }

    protected void doPost(HttpServletRequest r, HttpServletResponse s) throws IOException, ServletException {
        try {
            HttpSession session = r.getSession();
            Object storedCart = session.getAttribute("cart");
            Map<Integer, CartItem> cart = new LinkedHashMap<>();
            if (storedCart instanceof Map<?, ?>) {
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) storedCart).entrySet()) {
                    if (!(entry.getKey() instanceof Integer) || !(entry.getValue() instanceof CartItem))
                        throw new IllegalStateException("Invalid cart contents");
                    cart.put((Integer) entry.getKey(), (CartItem) entry.getValue());
                }
            }
            session.setAttribute("cart", cart);
            int id = Integer.parseInt(r.getParameter("id"));
            String action = r.getParameter("action");
            if ("remove".equals(action))
                cart.remove(id);
            else {
                Product p = new ProductService().get(id);
                int q = cart.containsKey(id) ? cart.get(id).getQuantity() : 0;
                int requested = "update".equals(action) ? Integer.parseInt(r.getParameter("qty")) : q + 1;
                if (requested <= 0)
                    cart.remove(id);
                else
                    cart.put(id, new CartItem(p, Math.min(requested, p.getStock())));
            }
            s.sendRedirect("cart");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
