package com.bromel.ejb.service;

import com.bromel.ejb.entities.Book;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;


@Stateless
public class BookService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public List<Book> getAllBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
}
