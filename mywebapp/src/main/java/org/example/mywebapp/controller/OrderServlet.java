package org.example.mywebapp.controller;


import com.bromel.ejb.entities.User;
import com.bromel.ejb.model.Cart;
import com.bromel.ejb.model.ShippingInfo;
import com.bromel.ejb.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ejb.EJB;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || session.getAttribute("cart") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String method = request.getParameter("method");
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        ShippingInfo shippingInfo = (ShippingInfo) session.getAttribute("shippingInfo");

        if ("cod".equals(method)) {
            // Xử lý COD
            int orderId = orderService.createOrder(user.getId(), cart, "COD", shippingInfo);
            cart.clear(); // Xóa giỏ hàng sau khi đặt hàng
            session.setAttribute("orderId", orderId);
            response.sendRedirect(request.getContextPath() + "/order-success");
        } else if ("vnpay".equals(method)) {
            // Xử lý VNPay
            String vnpayUrl = generateVNPayUrl(request, cart, user.getId());
            response.sendRedirect(vnpayUrl);
        }
    }

    private String generateVNPayUrl(HttpServletRequest request, Cart cart, int userId) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TmnCode = "S50VJF3W"; // Thay bằng mã thực tế
        String vnp_HashSecret = "0ZZIYFGGBZB2WAL6326NTI95QEMB1ATU"; // Thay bằng mã thực tế
        String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String vnp_ReturnUrl = "http://localhost:8080" + request.getContextPath() + "/order-return"; // Thay bằng domain thực tế

        String orderId = String.valueOf(System.currentTimeMillis());
        double amount = cart.getTotal() * 100;
        String vnp_Amount = String.valueOf((int) amount);
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String vnp_IpAddr = request.getRemoteAddr();
        String vnp_OrderInfo = "Thanh toan don hang " + orderId;
        String vnp_TxnRef = orderId;

        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Amount", vnp_Amount);
        params.put("vnp_Command", vnp_Command);
        params.put("vnp_CreateDate", vnp_CreateDate);
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_IpAddr", vnp_IpAddr);
        params.put("vnp_Locale", "vn");
        params.put("vnp_OrderInfo", vnp_OrderInfo);
        params.put("vnp_OrderType", "other");
        params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        params.put("vnp_TmnCode", vnp_TmnCode);
        params.put("vnp_TxnRef", vnp_TxnRef);
        params.put("vnp_Version", vnp_Version);

        StringBuilder hashData = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            hashData.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        hashData.setLength(hashData.length() - 1);

        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        System.out.println("hashData: " + hashData.toString());
        System.out.println("vnp_SecureHash: " + vnp_SecureHash);

        return vnp_Url + "?" + hashData.toString() + "&vnp_SecureHash=" + vnp_SecureHash;
    }

    private String hmacSHA512(String key, String data) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance("HmacSHA512");
            mac.init(new javax.crypto.spec.SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA512"));
            byte[] hmac = mac.doFinal(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hmac) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC SHA512", e);
        }
    }
}
