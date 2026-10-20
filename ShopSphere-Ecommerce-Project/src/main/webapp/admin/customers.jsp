
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Customers | ShopSphere</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/admin.css">
</head>
<body>
<div class="admin-layout">
<aside class="sidebar">
<a class="brand" href="${pageContext.request.contextPath}/admin/dashboard">Shop<span class="brand-accent">Sphere</span></a>
<div class="sidebar-label">MAIN MENU</div>
<nav class="sidebar-nav">
<a class="nav-item" href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
<a class="nav-item" href="${pageContext.request.contextPath}/admin/products">Products</a>
<a class="nav-item" href="${pageContext.request.contextPath}/admin/orders">Orders</a>
<a class="nav-item active" href="${pageContext.request.contextPath}/admin/customers">Customers</a>
</nav>
<div class="sidebar-bottom">
<a class="nav-item" href="${pageContext.request.contextPath}/products">View Store</a>
<a class="nav-item" href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</aside>

<main class="main-content">
<header class="topbar">
<div><div class="breadcrumb-text">ShopSphere / People</div><h1 class="page-title">Customer Management</h1></div>
</header>

<section class="dashboard-content">
<div class="content-card">
<div class="card-heading">
<div><h3>Registered Accounts</h3><p>View customer contact information and account roles.</p></div>
<span class="badge text-bg-light"><c:out value="${customers.size()}"/> accounts</span>
</div>

<div class="table-responsive">
<table class="table orders-table align-middle">
<thead><tr><th>CUSTOMER</th><th>EMAIL</th><th>MOBILE</th><th>ACCOUNT ROLE</th><th>USER ID</th></tr></thead>
<tbody>
<c:forEach var="c" items="${customers}">
<tr>
<td>
<div class="customer-cell">
<div class="customer-avatar"><c:out value="${c.name.substring(0,1)}"/></div>
<strong><c:out value="${c.name}" default="Unnamed"/></strong>
</div>
</td>
<td><c:out value="${c.email}" default="—"/></td>
<td><c:out value="${c.mobile}" default="—"/></td>
<td>
<c:choose>
<c:when test="${c.role eq 'ADMIN'}"><span class="status-badge status-placed">Admin</span></c:when>
<c:otherwise><span class="status-badge status-other">Customer</span></c:otherwise>
</c:choose>
</td>
<td>#<c:out value="${c.id}"/></td>
</tr>
</c:forEach>
<c:if test="${empty customers}">
<tr><td colspan="5" class="text-center p-4">No customer accounts found.</td></tr>
</c:if>
</tbody>
</table>
</div>
</div>
</section>
</main>
</div>
</body>
</html>
