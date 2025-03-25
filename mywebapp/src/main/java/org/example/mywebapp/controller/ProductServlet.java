package org.example.mywebapp.controller;

import com.bromel.ejb.entities.Book;
import com.bromel.ejb.model.Cart;
import com.bromel.ejb.service.BookService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @EJB
    private BookService bookService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession(false);

        if ("add".equals(action)) {
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int quantity = Integer.parseInt(request.getParameter("quantity") != null ? request.getParameter("quantity") : "1");

            // Lấy thông tin sách từ database
            Book book = bookService.getAllBooks().stream()
                    .filter(b -> b.getId() == bookId)
                    .findFirst()
                    .orElse(null);

            if (book != null) {
                // Lấy hoặc tạo giỏ hàng từ session
                Cart cart = (Cart) session.getAttribute("cart");
                if (cart == null) {
                    cart = new Cart();
                    session.setAttribute("cart", cart);
                }
                cart.addItem(book, quantity);
            }
            response.sendRedirect(request.getContextPath() + "/products");
            return;
        }

        // Hiển thị danh sách sản phẩm
        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/jsp/product.jsp").forward(request, response);
    }
}