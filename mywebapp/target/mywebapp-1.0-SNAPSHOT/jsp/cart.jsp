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
    <title>Giỏ Hàng</title>
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/products">Sản phẩm</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
                </li>
                <%
                    if (sessionUser != null && sessionUser.getAttribute("user") != null) {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/order-history">Lịch sử đơn hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white" href="${pageContext.request.contextPath}/logout">Đăng
                        xuất</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>

<div class="container my-5">
    <h1 class="text-center mb-4">Giỏ Hàng</h1>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <c:if test="${empty cart.items}">
        <p class="text-center">Giỏ hàng của bạn đang trống.</p>
    </c:if>
    <c:if test="${not empty cart.items}">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Tên sách</th>
                <th>Giá</th>
                <th>Số lượng</th>
                <th>Tổng phụ</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>${item.book.title}</td>
                    <td>${item.book.price} VNĐ</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart" method="get" class="d-inline">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="bookId" value="${item.book.id}">
                            <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control d-inline" style="width: 80px;">
                            <button type="submit" class="btn btn-sm btn-primary">Cập nhật</button>
                        </form>
                    </td>
                    <td>${item.subtotal} VNĐ</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/cart?action=remove&bookId=${item.book.id}"
                           class="btn btn-danger btn-sm">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="3" class="text-end"><strong>Tổng cộng:</strong></td>
                <td colspan="2">${cart.total} VNĐ</td>
            </tr>
            </tfoot>
        </table>
        <div class="text-end">
            <a href="${pageContext.request.contextPath}/cart?action=checkout" class="btn btn-success">Thanh toán</a>
        </div>
    </c:if>
    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary mt-3">Tiếp tục mua sắm</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist" JSP
/bootstrap.bundle.min.js"></script>
</body>
</html>