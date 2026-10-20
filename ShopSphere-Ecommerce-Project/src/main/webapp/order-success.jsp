
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<div class="container py-5 text-center">
    <h2 class="text-success">Order placed successfully!</h2>
    <p>Your order has been saved.</p>
    <p>
        Order ID:
        <strong><c:out value="${param.orderId}"/></strong>
    </p>
    <p>
        Payment is pending. Mock UPI/Card options do not process real payments.
    </p>
    <a href="products" class="btn btn-success">Continue Shopping</a>
</div>

<%@ include file="fragments/footer.jsp" %>
