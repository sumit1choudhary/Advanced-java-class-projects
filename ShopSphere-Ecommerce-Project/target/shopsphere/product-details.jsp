<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ include file="fragments/header.jsp" %>
<c:set var="p" value="${product}"/><div class="row g-5 align-items-center"><div class="col-md-6"><img class="detail-img rounded-4 shadow" src="${p.imageUrl}" alt="${p.name}"></div>
<div class="col-md-6"><span class="badge bg-success">${p.discount}% OFF</span><h1>${p.name}</h1><p class="lead">${p.description}</p><p>Brand: <b>${p.brand}</b></p><h2>₹${p.finalPrice}</h2><del>₹${p.price}</del><p class="mt-3">Stock: ${p.stock}</p>
<form method="post" action="cart"><input type="hidden" name="id" value="${p.productId}"><button class="btn btn-primary btn-lg">Add to Cart</button></form></div></div><%@ include file="fragments/footer.jsp" %>
