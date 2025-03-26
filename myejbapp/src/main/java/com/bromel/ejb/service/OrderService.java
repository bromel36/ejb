package com.bromel.ejb.service;



import com.bromel.ejb.entities.Book;
import com.bromel.ejb.entities.Order;
import com.bromel.ejb.entities.OrderDetail;
import com.bromel.ejb.model.Cart;
import com.bromel.ejb.model.CartItem;
import com.bromel.ejb.model.ShippingInfo;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;

@Stateless
public class OrderService {



    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public Order createOrder(int userId, Cart cart, String paymentMethod, ShippingInfo shippingInfo) throws Exception {
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(cart.getTotal());
        order.setPaymentMethod(paymentMethod);
        order.setStatus("PENDING");
        order.setAddress(shippingInfo.getAddress());
        order.setReceiverName(shippingInfo.getName());
        order.setReceiverPhone(shippingInfo.getPhone());

        em.persist(order);

        for (CartItem item : cart.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getId());
            detail.setBookId(item.getBook().getId());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getBook().getPrice().doubleValue());
            em.persist(detail);

            Book book = em.find(Book.class, item.getBook().getId());
            book.setQuantity(book.getQuantity() - item.getQuantity());
            em.merge(book);
        }
        return order;
    }

    public void updateOrderStatus(int orderId, String status) {
        Order order = em.find(Order.class, orderId);
        if (order != null) {
            order.setStatus(status);
            em.merge(order);
        }
    }

    public List<Order> getOrdersByUserId(int id) {
        return em.createQuery("SELECT o FROM Order o WHERE o.userId = :userId", Order.class)
                .setParameter("userId", id)
                .getResultList();
    }

    public boolean checkQuantity(Cart cart) {
        HashMap<Integer, Book> books = new HashMap<>();
        for (CartItem item : cart.getItems()) {
            Book book = em.find(Book.class, item.getBook().getId());
            if (book.getQuantity() < item.getQuantity()) {
                return false;
            }
            books.put(item.getBook().getId(), book);
        }
        return true;
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        return em.createQuery("SELECT od FROM OrderDetail od WHERE od.orderId = :orderId", OrderDetail.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
    public Order getOrderById(int orderId) {
        return em.find(Order.class, orderId);
    }
}
