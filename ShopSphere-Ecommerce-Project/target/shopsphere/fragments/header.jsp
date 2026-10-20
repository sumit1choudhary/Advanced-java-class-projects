
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${empty pageTitle ? 'ShopSphere' : pageTitle}</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <div class="container">

        <a class="navbar-brand fw-bold"
           href="${pageContext.request.contextPath}/index.jsp">
            ShopSphere
        </a>

        <div class="ms-auto d-flex align-items-center flex-wrap gap-2">

            <!-- Shop -->
            
<c:if test="${not empty sessionScope.user and sessionScope.user.role eq 'ADMIN'}">
    <a class="btn btn-outline-light btn-sm"
       href="${pageContext.request.contextPath}/products">
        Shop
    </a>
</c:if>
<c:if test="${empty sessionScope.user}">
    <a class="btn btn-outline-light btn-sm"
       href="${pageContext.request.contextPath}/admin-login.jsp">
        Admin Login
    </a>
</c:if>

            <!-- Cart -->
            <a class="btn btn-warning btn-sm"
               href="${pageContext.request.contextPath}/cart">
                Cart 🛒
            </a>

            <c:choose>

                <c:when test="${not empty sessionScope.user}">

                    <!-- My Orders -->
                    <a class="btn btn-info btn-sm"
                       href="${pageContext.request.contextPath}/my-orders">
                        My Orders
                    </a>

                    <!-- Logged-in User -->
                    <span class="text-white">
                        Hi, <c:out value="${sessionScope.user.name}"/>
                    </span>

                    <!-- Logout -->
                    <a class="btn btn-outline-light btn-sm"
                       href="${pageContext.request.contextPath}/logout">
                        Logout
                    </a>

                </c:when>

                <c:otherwise>

                    <!-- Login -->
                    <a class="btn btn-light btn-sm"
                       href="${pageContext.request.contextPath}/login.jsp">
                        Login
                    </a>

                </c:otherwise>

            </c:choose>

        </div>
    </div>
</nav>

<main class="container py-4">
