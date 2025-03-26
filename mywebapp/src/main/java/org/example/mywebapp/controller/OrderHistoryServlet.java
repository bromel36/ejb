package org.example.mywebapp.controller;


import com.bromel.ejb.entities.Book;
import com.bromel.ejb.entities.Order;
import com.bromel.ejb.entities.OrderDetail;
import com.bromel.ejb.entities.User;
import com.bromel.ejb.service.BookService;
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
import java.util.List;
import java.util.Map;

@WebServlet("/order-history")
public class OrderHistoryServlet extends HttpServlet {

    @EJB
    private OrderService orderService;

    @EJB
    private BookService bookService;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.getOrdersByUserId(user.getId());

        Map<Integer, List<OrderDetail>> orderDetailsMap = new HashMap<>();
        Map<Integer, Book> bookMap = new HashMap<>();
        for (Order order : orders) {
            List<OrderDetail> details = orderService.getOrderDetails(order.getId());
            orderDetailsMap.put(order.getId(), details);
            for (OrderDetail detail : details) {
                if (!bookMap.containsKey(detail.getBookId())) {
                    Book book = bookService.getBookById(detail.getBookId());
                    bookMap.put(detail.getBookId(), book);
                }
            }
        }
        request.setAttribute("orders", orders);
        request.setAttribute("orderDetailsMap", orderDetailsMap);
        request.setAttribute("bookMap", bookMap);
        request.getRequestDispatcher("/jsp/order-history.jsp").forward(request, response);
    }
}
