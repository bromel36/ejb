package com.bromel.ejb.service;

import com.bromel.ejb.entities.Book;
import com.bromel.ejb.entities.OrderDetail;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Stateless
public class BookService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public List<Book> getAllBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Book getBookById(int bookId) {
        return em.find(Book.class, bookId);
    }

    public Map<Integer, Book> getBooksByOrderDetails(List<OrderDetail> orderDetails) {
        List<Integer> bookIds = orderDetails.stream().map(OrderDetail::getBookId).collect(Collectors.toList());
        List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.id IN :bookIds", Book.class)
                .setParameter("bookIds", bookIds)
                .getResultList();
        return books.stream().collect(Collectors.toMap(Book::getId, book -> book));
    }

}
