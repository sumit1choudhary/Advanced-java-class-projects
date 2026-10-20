<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="mb-4">
    <h2>Products</h2>
    <p class="text-muted">Find something you love.</p>
</div>

<form action="${pageContext.request.contextPath}/products" method="get"
      class="row g-3 mb-4 p-3 bg-white rounded-4 shadow-sm">
    <div class="col-md-4">
        <label class="form-label" for="q">Search</label>
        <input id="q" class="form-control" name="q" value="${param.q}"
               placeholder="Product name or brand">
    </div>
    <div class="col-md-2">
        <label class="form-label" for="category">Category</label>
        <select id="category" class="form-select" name="category">
            <option value="0">All categories</option>
            <option value="1" ${param.category == '1' ? 'selected' : ''}>Electronics</option>
            <option value="2" ${param.category == '2' ? 'selected' : ''}>Fashion</option>
            <option value="3" ${param.category == '3' ? 'selected' : ''}>Gaming</option>
            <option value="4" ${param.category == '4' ? 'selected' : ''}>Books</option>
        </select>
    </div>
    <div class="col-md-2">
        <label class="form-label" for="minPrice">Min price (₹)</label>
        <input id="minPrice" class="form-control" type="number" name="minPrice"
               min="0" step="0.01" value="${empty param.minPrice ? '0' : param.minPrice}">
    </div>
    <div class="col-md-2">
        <label class="form-label" for="maxPrice">Max price (₹)</label>
        <input id="maxPrice" class="form-control" type="number" name="maxPrice"
               min="0" step="0.01" value="${empty param.maxPrice ? '1000000' : param.maxPrice}">
    </div>
    <div class="col-md-2">
        <label class="form-label" for="sort">Sort by</label>
        <select id="sort" class="form-select" name="sort">
            <option value="" ${empty param.sort ? 'selected' : ''}>Newest</option>
            <option value="price_asc" ${param.sort == 'price_asc' ? 'selected' : ''}>Price: Low to High</option>
            <option value="price_desc" ${param.sort == 'price_desc' ? 'selected' : ''}>Price: High to Low</option>
        </select>
    </div>
    <div class="col-12 d-flex gap-2">
        <button type="submit" class="btn btn-dark">Apply Filters</button>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-secondary">Clear Filters</a>
    </div>
</form>

<c:if test="${not empty filterError}">
    <div class="alert alert-warning" role="alert"><c:out value="${filterError}"/></div>
</c:if>

<div class="row g-4">
    <c:forEach var="p" items="${products}">
        <div class="col-sm-6 col-lg-4">
            <div class="card product-card h-100">
                <img src="<c:out value='${p.imageUrl}'/>" class="card-img-top" alt="<c:out value='${p.name}'/>">
                <div class="card-body">
                    <span class="badge bg-success mb-2"><c:out value="${p.discount}"/>% OFF</span>
                    <h5><c:out value="${p.name}"/></h5>
                    <p class="small text-muted"><c:out value="${p.brand}"/></p>
                    <p><c:out value="${p.description}"/></p>
                    <h5>₹<c:out value="${p.finalPrice}"/></h5>
                    <del class="text-muted">₹<c:out value="${p.price}"/></del>
                    <div class="mt-3">
                        <a class="btn btn-outline-dark" href="${pageContext.request.contextPath}/product?id=${p.productId}">Details</a>
                        <c:if test="${p.stock > 0}">
                            <form class="d-inline" method="post" action="${pageContext.request.contextPath}/cart">
                                <input type="hidden" name="id" value="${p.productId}">
                                <button class="btn btn-primary" type="submit">Add to Cart</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    <c:if test="${empty products}">
        <div class="col-12"><div class="alert alert-info">No products match these filters. Try changing your search or clearing the filters.</div></div>
    </c:if>
</div>

<%@ include file="fragments/footer.jsp" %>
