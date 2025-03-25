<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*, jakarta.servlet.*, jakarta.servlet.http.*" %>
<%@ page import="com.bromel.ejb.entities.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    HttpSession sessionUser = request.getSession(false);
    User user = (User) sessionUser.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Trang Chủ - BookShop</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        .hero-section {
            background: linear-gradient(to right, #007bff, #00c4cc);
            color: white;
            padding: 60px 0;
        }
        .welcome-text {
            font-size: 1.2rem;
            margin-top: 10px;
        }
        .btn-custom {
            margin: 5px;
        }
        .featured-books .card {
            transition: transform 0.3s;
        }
        .featured-books .card:hover {
            transform: translateY(-10px);
        }
        .explore-section {
            background-color: #f8f9fa;
            padding: 40px 0;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home">BookShop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/products">Sản phẩm</a>
                </li>
                <%
                    if (sessionUser != null && sessionUser.getAttribute("user") != null) {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>

<section class="hero-section text-center">
    <div class="container">
        <h1>Xin chào, <%= user != null ? user.getFullName() : "Khách" %>!</h1>
        <p class="welcome-text">Chào mừng bạn đến với <strong>BookShop</strong> - nơi hội tụ những cuốn sách tuyệt vời!</p>
        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/products" class="btn btn-light btn-custom">Xem sản phẩm</a>
            <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-light btn-custom">Xem giỏ hàng</a>
        </div>
    </div>
</section>

<!-- Phần sản phẩm nổi bật -->
<section class="featured-books container my-5">
    <h2 class="text-center mb-4">Sản Phẩm Nổi Bật</h2>
    <div class="row">
        <c:forEach var="book" items="${featuredBooks}" begin="0" end="2">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text">
                            <strong>Tác giả:</strong> ${book.author != null ? book.author : "Không rõ"}<br>
                            <strong>Giá:</strong> ${book.price} VNĐ<br>
                            <strong>Mô tả:</strong> ${book.description != null ? book.description : "Không có mô tả"}
                        </p>
                        <a href="${pageContext.request.contextPath}/products?action=add&bookId=${book.id}"
                           class="btn btn-primary">Thêm vào giỏ hàng</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</section>

<!-- Phần khuyến khích khám phá -->
<section class="explore-section text-center">
    <div class="container">
        <h3>Khám Phá Thêm</h3>
        <p>Chúng tôi có hàng trăm cuốn sách đang chờ bạn khám phá. Đừng bỏ lỡ cơ hội sở hữu những tác phẩm tuyệt vời!</p>
        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary btn-lg">Xem tất cả sản phẩm</a>
    </div>
</section>

<!-- Phần giới thiệu -->
<div class="container my-5">
    <h2 class="text-center mb-4">Về BookShop</h2>
    <p class="text-center">Chúng tôi tự hào mang đến cho bạn những cuốn sách chất lượng từ các tác giả nổi tiếng trong và ngoài nước. Hãy khám phá ngay hôm nay!</p>
</div>

<footer class="bg-dark text-white text-center py-3">
    <p>© 2025 BookShop. All rights reserved.</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>