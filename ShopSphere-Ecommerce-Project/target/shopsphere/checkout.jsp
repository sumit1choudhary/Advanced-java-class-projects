
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="fragments/header.jsp" %>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp"/>
</c:if>

<h2>Checkout</h2>

<c:if test="${not empty sessionScope.checkoutError}">
    <div class="alert alert-danger">
        <c:out value="${sessionScope.checkoutError}"/>
    </div>
    <c:remove var="checkoutError" scope="session"/>
</c:if>

<c:choose>
    <c:when test="${empty sessionScope.cart}">
        <div class="alert alert-info">
            Your cart is empty. <a href="products">Continue shopping</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row g-4">
            <div class="col-md-7">
                <div class="card p-4">
                    <form action="${pageContext.request.contextPath}/place-order"
                          method="post">

                        <h5>Delivery Address</h5>

                        <label class="form-label" for="address">Address</label>
                        <input id="address" name="address" class="form-control mb-3"
                               maxlength="255" required>

                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label" for="city">City</label>
                                <input id="city" name="city"
                                       class="form-control" maxlength="80" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label" for="state">State</label>
                                <input id="state" name="state"
                                       class="form-control" maxlength="80">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label" for="pincode">Pincode</label>
                                <input id="pincode" name="pincode"
                                       class="form-control" inputmode="numeric"
                                       pattern="[0-9]{6}" maxlength="6" required>
                            </div>
                        </div>

                        <h5 class="mt-4">Payment</h5>
                        <select name="paymentMethod" class="form-select" required>
                            <option value="Cash on Delivery">Cash on Delivery</option>
                            <option value="Mock UPI">Mock UPI (demo only)</option>
                            <option value="Mock Card">Mock Card (demo only)</option>
                        </select>

                        <button type="submit" class="btn btn-success mt-4 w-100">
                            Place Order
                        </button>
                    </form>
                </div>
            </div>

            <div class="col-md-5">
                <div class="card p-4">
                    <h5>Order Summary</h5>

                    <c:set var="total" value="0"/>

                    <c:forEach var="item" items="${sessionScope.cart.values()}">
                        <p>
                            <c:out value="${item.product.name}"/>
                            × <c:out value="${item.quantity}"/>
                            <span class="float-end">
                                ₹<c:out value="${item.subtotal}"/>
                            </span>
                        </p>
                        <c:set var="total"
                               value="${total + item.subtotal}"/>
                    </c:forEach>

                    <hr>
                    <h4>Total <span class="float-end">₹${total}</span></h4>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="fragments/footer.jsp" %>
