package com.shopsphere.controller;
import com.shopsphere.service.AuthService; import com.shopsphere.model.User; import javax.servlet.*; import javax.servlet.http.*; import javax.servlet.annotation.*; import java.io.*;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {protected void doPost(HttpServletRequest r,HttpServletResponse s)throws IOException,ServletException{try{User u=new User();u.setName(r.getParameter("name"));u.setEmail(r.getParameter("email"));u.setPassword(r.getParameter("password"));u.setMobile(r.getParameter("mobile"));new AuthService().register(u);s.sendRedirect("login.jsp?registered=1");}catch(Exception e){r.setAttribute("error","Registration failed. Email may already exist.");r.getRequestDispatcher("/register.jsp").forward(r,s);}}}
