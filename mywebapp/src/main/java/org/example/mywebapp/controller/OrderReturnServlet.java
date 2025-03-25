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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/order-return")
public class OrderReturnServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        String vnp_HashSecret = "0ZZIYFGGBZB2WAL6326NTI95QEMB1ATU";
        Map<String, String> vnp_Params = new HashMap<>();
        for (String key : request.getParameterMap().keySet()) {
            vnp_Params.put(key, request.getParameter(key));
        }

        String vnp_SecureHash = vnp_Params.remove("vnp_SecureHash");
        String signData = buildSignData(vnp_Params);
        String computedHash = hmacSHA512(vnp_HashSecret, signData);
        System.out.println("vnp_Params: " + vnp_Params);
        System.out.println("signData: " + signData);
        System.out.println("computedHash: " + computedHash);

        if (computedHash.equals(vnp_SecureHash)) {
            String responseCode = vnp_Params.get("vnp_ResponseCode");
            String txnRef = vnp_Params.get("vnp_TxnRef");
            int orderId = orderService.createOrder(((User) session.getAttribute("user")).getId(),
                    (Cart) session.getAttribute("cart"), "VNPAY",
                    (ShippingInfo) session.getAttribute("shippingInfo"));

            if ("00".equals(responseCode)) {
                orderService.updateOrderStatus(orderId, "PAID");
                ((Cart) session.getAttribute("cart")).clear();
                session.setAttribute("orderId", orderId);
                response.sendRedirect(request.getContextPath() + "/order-success");
            } else {
                orderService.updateOrderStatus(orderId, "CANCELLED");
                request.setAttribute("error", "Thanh toán thất bại: " + responseCode);
                request.getRequestDispatcher("/jsp/order-failure.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", "Chữ ký không hợp lệ");
            request.getRequestDispatcher("/jsp/order-failure.jsp").forward(request, response);
        }
    }

    private String buildSignData(Map<String, String> params) {
        StringBuilder signData = new StringBuilder();
        params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> signData.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        return signData.substring(0, signData.length() - 1);
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
