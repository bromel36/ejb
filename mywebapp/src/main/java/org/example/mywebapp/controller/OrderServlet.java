package org.example.mywebapp.controller;


import com.bromel.ejb.entities.Book;
import com.bromel.ejb.entities.Order;
import com.bromel.ejb.entities.OrderDetail;
import com.bromel.ejb.entities.User;
import com.bromel.ejb.model.Cart;
import com.bromel.ejb.model.ShippingInfo;
import com.bromel.ejb.service.EmailService;
import com.bromel.ejb.service.OrderService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @EJB
    private EmailService emailService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || session.getAttribute("cart") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        ShippingInfo shippingInfo = (ShippingInfo) session.getAttribute("shippingInfo");
        Order order = null;
        try {
            order = orderService.createOrder(user.getId(), cart, "COD", shippingInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<OrderDetail> orderDetails = orderService.getOrderDetails(order.getId());
        emailService.sendOrderConfirmationEmail(user.getEmail(), order, orderDetails);
        cart.clear();
        session.setAttribute("orderId", order.getId());
        response.sendRedirect(request.getContextPath() + "/order-success");
    }
}
