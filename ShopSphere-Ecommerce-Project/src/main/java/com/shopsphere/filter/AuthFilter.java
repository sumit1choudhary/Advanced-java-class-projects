package com.shopsphere.filter;
import javax.servlet.*; import javax.servlet.http.*; import javax.servlet.annotation.*; import java.io.*;
@WebFilter(urlPatterns={"/cart","/checkout","/orders","/profile"})
public class AuthFilter implements Filter {public void doFilter(ServletRequest a,ServletResponse b,FilterChain c)throws IOException,ServletException{HttpServletRequest r=(HttpServletRequest)a;HttpServletResponse s=(HttpServletResponse)b;if(r.getSession().getAttribute("user")==null){s.sendRedirect(r.getContextPath()+"/login.jsp");return;}c.doFilter(a,b);}}
