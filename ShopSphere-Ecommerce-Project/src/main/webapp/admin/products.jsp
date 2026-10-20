
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Manage Products | ShopSphere</title>
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
<a class="nav-item active" href="${pageContext.request.contextPath}/admin/products">Products</a>
<a class="nav-item" href="${pageContext.request.contextPath}/admin/orders">Orders</a>
<a class="nav-item" href="${pageContext.request.contextPath}/admin/customers">Customers</a>
</nav>
<div class="sidebar-bottom">
<a class="nav-item" href="${pageContext.request.contextPath}/products">View Store</a>
<a class="nav-item" href="${pageContext.request.contextPath}/logout">Logout</a>
</div>
</aside>

<main class="main-content">
<header class="topbar">
<div>
<div class="breadcrumb-text">ShopSphere / Inventory</div>
<h1 class="page-title">Product Management</h1>
</div>
</header>

<section class="dashboard-content">
<c:set var="editing" value="${not empty editProduct}"/>

<div class="content-card mb-4">
<div class="card-heading">
<div>
<h3><c:choose><c:when test="${editing}">Edit Product</c:when><c:otherwise>Add New Product</c:otherwise></c:choose></h3>
<p>Manage product details, pricing, images and inventory.</p>
</div>
</div>
<div class="p-4">
<form method="post" action="${pageContext.request.contextPath}/admin/products">
<input type="hidden" name="action" value="save">
<input type="hidden" name="productId" value="${editProduct.productId}">

<div class="row g-3">
<div class="col-md-6">
<label class="form-label">Product Name *</label>
<input class="form-control" name="name" required maxlength="200" value="<c:out value='${editProduct.name}'/>">
</div>
<div class="col-md-6">
<label class="form-label">Brand</label>
<input class="form-control" name="brand" maxlength="100" value="<c:out value='${editProduct.brand}'/>">
</div>
<div class="col-md-6">
<label class="form-label">Category *</label>
<select class="form-select" name="categoryId" required>
<option value="">Select category</option>
<c:forEach var="cat" items="${categories}">
<option value="${cat.id}" <c:if test="${editProduct.categoryId == cat.id}">selected</c:if>>
<c:out value="${cat.name}"/>
</option>
</c:forEach>
</select>
</div>
<div class="col-md-3">
<label class="form-label">Price (₹) *</label>
<input class="form-control" type="number" name="price" min="0" step="0.01" required value="${editProduct.price}">
</div>
<div class="col-md-3">
<label class="form-label">Discount (%)</label>
<input class="form-control" type="number" name="discount" min="0" max="100" step="0.01" value="${empty editProduct.discount ? '0' : editProduct.discount}">
</div>
<div class="col-md-4">
<label class="form-label">Stock quantity *</label>
<input class="form-control" type="number" name="stock" min="0" required value="${editProduct.stock}">
</div>
<div class="col-md-4">
<label class="form-label">Visibility</label>
<select class="form-select" name="status">
<option value="1" <c:if test="${empty editProduct or editProduct.status == 1}">selected</c:if>>Active</option>
<option value="0" <c:if test="${editProduct.status == 0}">selected</c:if>>Hidden</option>
</select>
</div>
<div class="col-md-4">
<label class="form-label">Image URL or path</label>
<input class="form-control" name="imageUrl" value="<c:out value='${editProduct.imageUrl}'/>" placeholder="https://... or images/product.jpg">
</div>
<div class="col-12">
<label class="form-label">Description</label>
<textarea class="form-control" name="description" rows="3"><c:out value="${editProduct.description}"/></textarea>
</div>
</div>

<div class="mt-4 d-flex gap-2">
<button class="btn btn-primary" type="submit">
<c:choose><c:when test="${editing}">Save Changes</c:when><c:otherwise>Add Product</c:otherwise></c:choose>
</button>
<c:if test="${editing}">
<a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/products">Cancel</a>
</c:if>
</div>
</form>
</div>
</div>

<div class="content-card">
<div class="card-heading">
<div><h3>All Products</h3><p>Products registered in your store</p></div>
<span class="badge text-bg-light"><c:out value="${products.size()}"/> products</span>
</div>
<div class="table-responsive">
<table class="table orders-table align-middle">
<thead><tr><th>ID</th><th>PRODUCT</th><th>CATEGORY</th><th>PRICE</th><th>STOCK</th><th>STATUS</th><th>ACTIONS</th></tr></thead>
<tbody>
<c:forEach var="p" items="${products}">
<tr>
<td>#<c:out value="${p.productId}"/></td>
<td>
<div class="d-flex align-items-center gap-2">
<c:if test="${not empty p.imageUrl}">
<img src="<c:out value='${p.imageUrl}'/>" alt="" style="width:48px;height:48px;object-fit:contain;border:1px solid #eee;border-radius:7px" onerror="this.style.display='none'">
</c:if>
<div><strong><c:out value="${p.name}"/></strong><br><small class="text-muted"><c:out value="${p.brand}"/></small></div>
</div>
</td>
<td><c:out value="${p.categoryName}" default="Uncategorised"/></td>
<td>₹<c:out value="${p.price}"/></td>
<td><c:out value="${p.stock}"/></td>
<td>
<c:choose><c:when test="${p.status == 1}"><span class="status-badge status-delivered">Active</span></c:when><c:otherwise><span class="status-badge status-cancelled">Hidden</span></c:otherwise></c:choose>
</td>
<td>
<div class="d-flex gap-2">
<a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/products?edit=${p.productId}">Edit</a>
<form method="post" action="${pageContext.request.contextPath}/admin/products" onsubmit="return confirm('Hide this product from the store?')">
<input type="hidden" name="action" value="delete">
<input type="hidden" name="productId" value="${p.productId}">
<button class="btn btn-sm btn-outline-danger" type="submit">Hide</button>
</form>
</div>
</td>
</tr>
</c:forEach>
<c:if test="${empty products}">
<tr><td colspan="7" class="text-center p-4">No products found.</td></tr>
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
