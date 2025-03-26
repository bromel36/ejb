<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    HttpSession sessionUser = request.getSession(false);
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh Sách Sản Phẩm</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}">BookShop</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/products">Sản phẩm</a>
                </li>

                <%
                    if (sessionUser != null && sessionUser.getAttribute("user") != null) {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/order-history">Lịch sử đơn hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </li>
                <%
                } else{
                %>
                <li class="nav-item">
                    <a class="nav-link btn btn-primary" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>

<div class="container my-5">
    <h1 class="text-center mb-4">Danh Sách Sản Phẩm</h1>
    <div class="row">
        <c:forEach var="book" items="${books}">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text">
                            <strong>Tác giả:</strong> ${book.author != null ? book.author : "Không rõ"}<br>
                            <strong>Giá:</strong> ${book.price} VNĐ<br>
                            <strong>Số lượng:</strong> ${book.quantity}<br>
                            <strong>Mô tả:</strong> ${book.description != null ? book.description : "Không có mô tả"}
                        </p>
                        <a href="${pageContext.request.contextPath}/products?action=add&bookId=${book.id}"
                           class="btn btn-primary">Thêm vào giỏ hàng</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>