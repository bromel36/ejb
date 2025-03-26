package org.example.mywebapp.controller;


import com.bromel.ejb.entities.Book;
import com.bromel.ejb.model.Cart;
import com.bromel.ejb.model.CartItem;
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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }


        String action = request.getParameter("action");
        if ("remove".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            cart.removeItem(bookId);
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        else if ("update".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            for (CartItem item : cart.getItems()) {
                if (item.getBook().getId() == bookId) {
                    item.setQuantity(quantity > 0 ? quantity : 1); // Đảm bảo số lượng tối thiểu là 1
                    break;
                }
            }
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        else if ("checkout".equals(action)) {
            if (orderService.checkQuantity(cart)){
                response.sendRedirect(request.getContextPath() + "/checkout");
                return;
            }
            else{
                response.sendRedirect(request.getContextPath() + "/cart?message=Existing item exceeded");
            }
        }


        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
    }
}
