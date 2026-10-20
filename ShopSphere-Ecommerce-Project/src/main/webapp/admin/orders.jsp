
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Manage Orders | ShopSphere</title>
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
<a class="nav-item active" href="${pageContext.request.contextPath}/admin/orders">Orders</a>
<a class="nav-item" href="${pageContext.request.contextPath}/admin/customers">Customers</a>
</nav>
<div class="sidebar-bottom">
<a class="nav-item" href="${pageContext.request.contextPath}/products">View Store</a>
<a class="nav-item" href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</aside>

<main class="main-content">
<header class="topbar">
<div><div class="breadcrumb-text">ShopSphere / Sales</div><h1 class="page-title">Order Management</h1></div>
</header>

<section class="dashboard-content">
<div class="content-card">
<div class="card-heading">
<div><h3>All Orders</h3><p>Review orders and manage fulfilment status.</p></div>
<span class="badge text-bg-light"><c:out value="${orders.size()}"/> orders</span>
</div>
<div class="table-responsive">
<table class="table orders-table align-middle">
<thead><tr><th>ORDER</th><th>CUSTOMER</th><th>DATE</th><th>TOTAL</th><th>PAYMENT</th><th>ORDER STATUS</th><th>UPDATE STATUS</th></tr></thead>
<tbody>
<c:forEach var="o" items="${orders}">
<tr>
<td class="order-number">#<c:out value="${o.id}"/></td>
<td><strong><c:out value="${o.customer}" default="Unknown"/></strong><br><small class="text-muted"><c:out value="${o.email}"/></small></td>
<td><c:out value="${o.date}"/></td>
<td class="amount-cell">₹<c:out value="${o.total}"/></td>
<td><c:out value="${o.paymentStatus}" default="—"/><br><small class="text-muted"><c:out value="${o.paymentMethod}" default=""/></small></td>
<td><span class="status-badge status-other"><c:out value="${o.status}" default="Unknown"/></span></td>
<td>
<form method="post" action="${pageContext.request.contextPath}/admin/orders" class="d-flex gap-2">
<input type="hidden" name="orderId" value="${o.id}">
<select class="form-select form-select-sm" name="status" aria-label="Order status">
<option value="PLACED" <c:if test="${o.status eq 'PLACED'}">selected</c:if>>Placed</option>
<option value="PROCESSING" <c:if test="${o.status eq 'PROCESSING'}">selected</c:if>>Processing</option>
<option value="SHIPPED" <c:if test="${o.status eq 'SHIPPED'}">selected</c:if>>Shipped</option>
<option value="DELIVERED" <c:if test="${o.status eq 'DELIVERED'}">selected</c:if>>Delivered</option>
<option value="CANCELLED" <c:if test="${o.status eq 'CANCELLED'}">selected</c:if>>Cancelled</option>
</select>
<button class="btn btn-sm btn-primary" type="submit">Save</button>
</form>
</td>
</tr>
</c:forEach>
<c:if test="${empty orders}">
<tr><td colspan="7" class="text-center p-4">No orders found.</td></tr>
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
