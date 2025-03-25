<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đặt Hàng Thành Công</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container my-5 text-center">
    <h1>Đặt hàng thành công!</h1>
    <p>Cảm ơn bạn đã mua sắm tại BookShop. Mã đơn hàng của bạn là: <strong>${sessionScope.orderId}</strong>.</p>
    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Quay về trang chủ</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>