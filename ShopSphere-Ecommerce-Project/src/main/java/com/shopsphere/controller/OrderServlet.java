 
package com.shopsphere.controller;

import com.shopsphere.dao.OrderDAO;
import com.shopsphere.model.CartItem;
import com.shopsphere.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/place-order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || !(session.getAttribute("user") instanceof User)) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart =
            (Map<Integer, CartItem>) session.getAttribute("cart");

        try {
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String pincode = request.getParameter("pincode");
            String paymentMethod = request.getParameter("paymentMethod");

            int orderId = new OrderDAO().placeOrder(
                user.getUserId(),
                address,
                city,
                state,
                pincode,
                paymentMethod,
                cart
            );

            // Clear cart only after successful database commit.
            session.removeAttribute("cart");

            response.sendRedirect(
                request.getContextPath() + "/order-success.jsp?orderId=" + orderId
            );

        } catch (Exception e) {
            getServletContext().log("Checkout failed", e);
            session.setAttribute("checkoutError",
                "Order could not be placed: " + e.getMessage());
            response.sendRedirect(
                request.getContextPath() + "/checkout.jsp"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {
        response.sendRedirect(request.getContextPath() + "/checkout.jsp");
    }
}
