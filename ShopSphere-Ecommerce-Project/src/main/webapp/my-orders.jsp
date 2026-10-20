
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>My Orders</h2>
        <a href="${pageContext.request.contextPath}/products"
           class="btn btn-dark">
            Continue Shopping
        </a>
    </div>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="text-center py-5">
                <h4>No orders yet</h4>
                <p class="text-muted">Your orders will appear here.</p>
                <a href="${pageContext.request.contextPath}/products"
                   class="btn btn-primary">
                    Start Shopping
                </a>
            </div>
        </c:when>

        <c:otherwise>
            <c:forEach var="o" items="${orders}">
                <div class="card mb-3 shadow-sm">
                    <div class="card-body">

                        <div class="d-flex justify-content-between flex-wrap mb-3">
                            <div>
                                <strong>Order #<c:out value="${o.orderId}"/></strong>
                                <div class="text-muted small">
                                    <c:out value="${o.createdAt}"/>
                                </div>
                            </div>

                            <div class="text-end">
                                <span class="badge bg-secondary">
                                    <c:out value="${o.status}"/>
                                </span>
                                <div class="fw-bold mt-2">
                                    ₹<c:out value="${o.total}"/>
                                </div>
                            </div>
                        </div>

                        <c:forEach var="item" items="${o.items}">
                            <div class="d-flex align-items-center gap-3 border-top py-3">

                                <c:choose>
                                    <c:when test="${not empty item.image}">
                                        <img
                                            src="${item.image}"
                                            alt="Product"
                                            style="width:60px;height:60px;object-fit:contain"
                                            onerror="this.style.display='none'">
                                    </c:when>
                                    <c:otherwise>
                                        <div style="font-size:30px">📦</div>
                                    </c:otherwise>
                                </c:choose>

                                <div class="flex-grow-1">
                                    <div class="fw-semibold">
                                        <c:out value="${item.name}"
                                               default="Product unavailable"/>
                                    </div>
                                    <small class="text-muted">
                                        Quantity: <c:out value="${item.quantity}"/>
                                    </small>
                                </div>

                                <div class="fw-semibold">
                                    ₹<c:out value="${item.price}"/>
                                </div>
                            </div>
                        </c:forEach>

                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</div>

<%@ include file="fragments/footer.jsp" %>
