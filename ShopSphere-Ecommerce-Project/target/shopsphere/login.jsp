<%@ page contentType="text/html;charset=UTF-8" %><%@ include file="fragments/header.jsp" %>
<div class="auth-card card shadow-sm p-4 mx-auto"><h2>Welcome back</h2><p class="text-muted">Login to ShopSphere</p>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if><c:if test="${param.registered == '1'}"><div class="alert alert-success">Registration successful. Please login.</div></c:if>
<form method="post" action="login"><input class="form-control mb-3" type="email" name="email" placeholder="Email" required><input class="form-control mb-3" type="password" name="password" placeholder="Password" required><button class="btn btn-primary w-100">Login</button></form>
<p class="mt-3">New customer? <a href="register.jsp">Create account</a></p></div><%@ include file="fragments/footer.jsp" %>
