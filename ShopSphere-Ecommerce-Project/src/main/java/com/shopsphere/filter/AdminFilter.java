package com.shopsphere.filter;
import javax.servlet.*; import javax.servlet.http.*; import javax.servlet.annotation.*; import java.io.*;
@WebFilter(urlPatterns={"/admin/*"}) public class AdminFilter implements Filter {public void doFilter(ServletRequest a,ServletResponse b,FilterChain c)throws IOException,ServletException{HttpServletRequest r=(HttpServletRequest)a;HttpServletResponse s=(HttpServletResponse)b;Object role=r.getSession().getAttribute("role");if(!"ADMIN".equals(role)){s.sendError(403,"Admin access required");return;}c.doFilter(a,b);}}
