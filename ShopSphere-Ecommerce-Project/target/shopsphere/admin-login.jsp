
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Login | ShopSphere</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center"
     style="min-height:100vh">

    <div class="card shadow border-0 rounded-4 p-4"
         style="width:100%;max-width:420px">

        <div class="text-center mb-4">
            <h2 class="fw-bold">ShopSphere</h2>
            <h5 class="text-secondary">Admin Login</h5>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/admin-login"
      method="post">

            <div class="mb-3">
                <label class="form-label">Admin Email</label>
                <input type="email" name="email"
                       class="form-control" required>
            </div>

            <div class="mb-4">
                <label class="form-label">Password</label>
                <input type="password" name="password"
                       class="form-control" required>
            </div>

            <button type="submit"
                    class="btn btn-dark w-100">
                Login as Admin
            </button>
        </form>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/login.jsp">
                Customer Login
            </a>
        </div>

    </div>
</div>

</body>
</html>
