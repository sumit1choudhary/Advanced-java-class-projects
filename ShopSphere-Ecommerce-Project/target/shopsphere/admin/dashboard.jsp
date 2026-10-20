
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | ShopSphere</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
          rel="stylesheet">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/admin/admin.css">
</head>

<body>
<div class="admin-layout">

    <!-- Sidebar -->
    <aside class="sidebar">
        <a class="brand" href="${pageContext.request.contextPath}/admin/dashboard">
            <span class="brand-icon">
                <i class="fa-solid fa-bag-shopping"></i>
            </span>
            <span>Shop<span class="brand-accent">Sphere</span></span>
        </a>

        <div class="sidebar-label">MAIN MENU</div>

        <nav class="sidebar-nav">
            <a class="nav-item active"
               href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="fa-solid fa-chart-pie"></i>
                <span>Dashboard</span>
            </a>

            <a class="nav-item"
               href="${pageContext.request.contextPath}/admin/products">
                <i class="fa-solid fa-box"></i>
                <span>Products</span>
            </a>

            <a class="nav-item"
               href="${pageContext.request.contextPath}/admin/orders">
                <i class="fa-solid fa-receipt"></i>
                <span>Orders</span>
            </a>

            <a class="nav-item"
               href="${pageContext.request.contextPath}/admin/customers">
                <i class="fa-solid fa-users"></i>
                <span>Customers</span>
            </a>
        </nav>

        <div class="sidebar-bottom">
            <div class="sidebar-label">STORE</div>

            <a class="nav-item"
               href="${pageContext.request.contextPath}/products">
                <i class="fa-solid fa-store"></i>
                <span>View Store</span>
                <i class="fa-solid fa-arrow-up-right-from-square ms-auto small"></i>
            </a>

            <a class="nav-item"
               href="${pageContext.request.contextPath}/logout">
                <i class="fa-solid fa-right-from-bracket"></i>
                <span>Logout</span>
            </a>
        </div>

        <div class="sidebar-version">
            <span class="online-dot"></span>
            ShopSphere Admin
            <div class="version-number">Management Console</div>
        </div>
    </aside>

    <!-- Main Content -->
    <main class="main-content">

        <header class="topbar">
            <div>
                <div class="breadcrumb-text">ShopSphere / Dashboard</div>
                <h1 class="page-title">Dashboard Overview</h1>
            </div>

            <div class="admin-profile">
                <div class="profile-avatar">
                    <i class="fa-solid fa-user-shield"></i>
                </div>
                <div class="profile-info">
                    <div class="profile-name">Administrator</div>
                    <div class="profile-role">Store Management</div>
                </div>
            </div>
        </header>

        <section class="dashboard-content">

            <!-- Welcome -->
            <div class="welcome-row">
                <div>
                    <h2>Welcome back, Admin 👋</h2>
                    <p>Here's what's happening with your store today.</p>
                </div>

                <a href="${pageContext.request.contextPath}/products"
                   class="btn btn-store">
                    <i class="fa-solid fa-store me-2"></i>
                    Visit Store
                </a>
            </div>

            <!-- Statistics -->
            <div class="stats-grid">

                <div class="stat-card">
                    <div class="stat-top">
                        <div class="stat-icon purple">
                            <i class="fa-solid fa-box-open"></i>
                        </div>
                        <span class="stat-label">ACTIVE PRODUCTS</span>
                    </div>
                    <div class="stat-value">
                        <c:out value="${totalProducts}" default="0"/>
                    </div>
                    <div class="stat-caption">
                        Products available in your store
                    </div>
                    <a class="small mt-3 d-inline-block"
                       href="${pageContext.request.contextPath}/admin/products">
                        Manage products →
                    </a>
                </div>

                <div class="stat-card">
                    <div class="stat-top">
                        <div class="stat-icon blue">
                            <i class="fa-solid fa-cart-shopping"></i>
                        </div>
                        <span class="stat-label">TOTAL ORDERS</span>
                    </div>
                    <div class="stat-value">
                        <c:out value="${totalOrders}" default="0"/>
                    </div>
                    <div class="stat-caption">
                        All orders placed in your store
                    </div>
                    <a class="small mt-3 d-inline-block"
                       href="${pageContext.request.contextPath}/admin/orders">
                        Manage orders →
                    </a>
                </div>

                <div class="stat-card">
                    <div class="stat-top">
                        <div class="stat-icon green">
                            <i class="fa-solid fa-users"></i>
                        </div>
                        <span class="stat-label">CUSTOMERS</span>
                    </div>
                    <div class="stat-value">
                        <c:out value="${totalCustomers}" default="0"/>
                    </div>
                    <div class="stat-caption">
                        Registered customer accounts
                    </div>
                    <a class="small mt-3 d-inline-block"
                       href="${pageContext.request.contextPath}/admin/customers">
                        View customers →
                    </a>
                </div>

                <div class="stat-card">
                    <div class="stat-top">
                        <div class="stat-icon orange">
                            <i class="fa-solid fa-indian-rupee-sign"></i>
                        </div>
                        <span class="stat-label">ORDER REVENUE</span>
                    </div>
                    <div class="stat-value revenue-value">
                        ₹<c:out value="${totalRevenue}" default="0.00"/>
                    </div>
                    <div class="stat-caption">
                        Excludes cancelled orders
                    </div>
                </div>

            </div>

            <!-- Recent Orders -->
            <section class="content-card">

                <div class="card-heading">
                    <div>
                        <h3>Recent Orders</h3>
                        <p>Latest orders from your customers</p>
                    </div>

                    <a href="${pageContext.request.contextPath}/admin/orders"
                       class="view-all">
                        View all orders
                        <i class="fa-solid fa-arrow-right ms-1"></i>
                    </a>
                </div>

                <div class="table-responsive">
                    <table class="table orders-table align-middle">
                        <thead>
                            <tr>
                                <th>ORDER ID</th>
                                <th>CUSTOMER</th>
                                <th>DATE</th>
                                <th>AMOUNT</th>
                                <th>ORDER STATUS</th>
                                <th>PAYMENT</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:choose>
                                <c:when test="${empty recentOrders}">
                                    <tr>
                                        <td colspan="6" class="empty-state">
                                            <i class="fa-solid fa-inbox"></i>
                                            <div>No orders found yet.</div>
                                        </td>
                                    </tr>
                                </c:when>

                                <c:otherwise>
                                    <c:forEach var="o" items="${recentOrders}">
                                        <tr>
                                            <td>
                                                <span class="order-number">
                                                    #<c:out value="${o.orderId}"/>
                                                </span>
                                            </td>

                                            <td>
                                                <div class="customer-cell">
                                                    <div class="customer-avatar">
                                                        <i class="fa-solid fa-user"></i>
                                                    </div>
                                                    <span>
                                                        <c:out value="${o.customerName}"
                                                               default="Guest"/>
                                                    </span>
                                                </div>
                                            </td>

                                            <td class="date-cell">
                                                <c:out value="${o.createdAt}"
                                                       default="—"/>
                                            </td>

                                            <td class="amount-cell">
                                                ₹<c:out value="${o.total}"
                                                        default="0.00"/>
                                            </td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${o.status eq 'PLACED'}">
                                                        <span class="status-badge status-placed">Placed</span>
                                                    </c:when>
                                                    <c:when test="${o.status eq 'PROCESSING'}">
                                                        <span class="status-badge status-other">Processing</span>
                                                    </c:when>
                                                    <c:when test="${o.status eq 'SHIPPED'}">
                                                        <span class="status-badge status-shipped">Shipped</span>
                                                    </c:when>
                                                    <c:when test="${o.status eq 'DELIVERED'}">
                                                        <span class="status-badge status-delivered">Delivered</span>
                                                    </c:when>
                                                    <c:when test="${o.status eq 'CANCELLED'}">
                                                        <span class="status-badge status-cancelled">Cancelled</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="status-badge status-other">
                                                            <c:out value="${o.status}" default="Unknown"/>
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>

                                            <td>
                                                <c:choose>
                                                    <c:when test="${o.paymentStatus eq 'PAID'}">
                                                        <span class="payment-paid">
                                                            <i class="fa-solid fa-circle-check me-1"></i>Paid
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${o.paymentStatus eq 'PENDING'}">
                                                        <span class="payment-pending">
                                                            <i class="fa-regular fa-clock me-1"></i>Pending
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">
                                                            <c:out value="${o.paymentStatus}" default="—"/>
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

            </section>

            <footer class="dashboard-footer">
                <span>© ShopSphere Admin Panel</span>
                <span>Store Management Console</span>
            </footer>

        </section>
    </main>
</div>
</body>
</html>
