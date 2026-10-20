package com.shopsphere.listener;
import javax.servlet.*; import javax.servlet.annotation.*;
@WebListener public class AppListener implements ServletContextListener {
 public void contextInitialized(ServletContextEvent e){e.getServletContext().setAttribute("appName","ShopSphere");}
 public void contextDestroyed(ServletContextEvent e){}
}
