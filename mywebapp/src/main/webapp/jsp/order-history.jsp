<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lịch Sử Đơn Hàng - BookShop</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/home">Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/products">Sản phẩm</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/order-history">Lịch sử đơn hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link btn btn-danger text-white" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container my-5">
    <h1 class="text-center mb-4">Lịch Sử Đơn Hàng</h1>
    <c:if test="${empty orders}">
        <p class="text-center">Bạn chưa có đơn hàng nào.</p>
    </c:if>
    <c:if test="${not empty orders}">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Mã đơn hàng</th>
                <th>Ngày đặt</th>
                <th>Trạng thái</th>
                <th>Tổng tiền</th>
                <th>Phương thức thanh toán</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.status}</td>
                    <td>${order.totalAmount} VNĐ</td>
                    <td>${order.paymentMethod}</td>
                    <td>
                        <button type="button" class="btn btn-primary btn-sm" data-bs-toggle="modal"
                                data-bs-target="#orderDetailModal${order.id}">
                            Xem chi tiết
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <!-- Modal cho từng đơn hàng -->
        <c:forEach var="order" items="${orders}">
            <div class="modal fade" id="orderDetailModal${order.id}" tabindex="-1" aria-labelledby="orderDetailModalLabel${order.id}" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="orderDetailModalLabel${order.id}">Chi tiết đơn hàng #${order.id}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p><strong>Ngày đặt:</strong> ${order.orderDate}</p>
                            <p><strong>Trạng thái:</strong> ${order.status}</p>
                            <p><strong>Phương thức thanh toán:</strong> ${order.paymentMethod}</p>
                            <h6>Chi tiết sản phẩm:</h6>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Tên sách</th>
                                    <th>Số lượng</th>
                                    <th>Giá</th>
                                    <th>Tổng phụ</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="detail" items="${orderDetailsMap[order.id]}">
                                    <tr>
                                        <td>${bookMap[detail.bookId].title}</td>
                                        <td>${detail.quantity}</td>
                                        <td>${detail.price} VNĐ</td>
                                        <td>${detail.price * detail.quantity} VNĐ</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <p><strong>Tổng tiền:</strong> ${order.totalAmount} VNĐ</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:if>
    <div class="text-center">
        <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">Tiếp tục mua sắm</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>