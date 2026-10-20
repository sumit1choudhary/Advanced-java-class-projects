package com.shopsphere.controller;
import com.shopsphere.service.ProductService; import javax.servlet.*; import javax.servlet.http.*; import javax.servlet.annotation.*; import java.io.*;
@WebServlet("/product") public class ProductDetailsServlet extends HttpServlet {protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException,ServletException{try{r.setAttribute("product",new ProductService().get(Integer.parseInt(r.getParameter("id"))));r.getRequestDispatcher("/product-details.jsp").forward(r,s);}catch(Exception e){throw new ServletException(e);}}}
