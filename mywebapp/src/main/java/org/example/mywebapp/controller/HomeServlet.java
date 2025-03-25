package org.example.mywebapp.controller;

import com.bromel.ejb.entities.Book;
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

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private BookService bookService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> featuredBooks = bookService.getAllBooks();
        request.setAttribute("featuredBooks", featuredBooks);

        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }
}
