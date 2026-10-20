# ShopSphere — RTU Advanced Java E-Commerce Project

## Stack
Java 17, Java EE Servlet/JSP (`javax.*`), JSTL 1.2, MySQL, JDBC, Maven, Apache Tomcat 9.

## Run
1. Install JDK 17+, Maven, MySQL 8+, and Tomcat 9.0.x.
2. Run `database/shopsphere.sql` in MySQL.
3. Open `src/main/java/com/shopsphere/util/DB.java` and set your MySQL username/password.
4. In the project folder run: `mvn clean package`
5. Copy `target/shopsphere.war` to Tomcat's `webapps` folder.
6. Start Tomcat.
7. Open `http://localhost:8080/shopsphere/`

## Core RTU coverage
Servlet/JSP, JDBC/DAO, MVC/layered architecture, HTTP/Tomcat, sessions, cookies-ready structure, authentication/admin filters, ServletContextListener, EL/JSTL, fragments, MySQL.

## Important
The guide recommends completing the core web flow before adding Socket, RMI, Swing, JNDI and other syllabus demonstrations. Those modules are planned as the next extension phase.
