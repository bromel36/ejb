package org.example.mywebapp.controller;



import com.bromel.ejb.model.Cart;
import com.bromel.ejb.model.ShippingInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || session.getAttribute("cart") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/jsp/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String paymentMethod = request.getParameter("paymentMethod");

        // Lưu thông tin giao hàng vào session để sử dụng sau
        session.setAttribute("shippingInfo", new ShippingInfo(name, address, phone));

        if ("COD".equals(paymentMethod)) {
            // Chuyển hướng đến servlet xử lý COD
            response.sendRedirect(request.getContextPath() + "/order?method=cod");
        } else if ("VNPAY".equals(paymentMethod)) {
            // Chuyển hướng đến servlet xử lý VNPay
            response.sendRedirect(request.getContextPath() + "/order?method=vnpay");
        }
    }
}
