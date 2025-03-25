package com.bromel.ejb.service;



import com.bromel.ejb.entities.Order;
import com.bromel.ejb.entities.OrderDetail;
import com.bromel.ejb.model.Cart;
import com.bromel.ejb.model.CartItem;
import com.bromel.ejb.model.ShippingInfo;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class OrderService {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public int createOrder(int userId, Cart cart, String paymentMethod, ShippingInfo shippingInfo) {
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
        }
        return order.getId();
    }

    public void updateOrderStatus(int orderId, String status) {
        Order order = em.find(Order.class, orderId);
        if (order != null) {
            order.setStatus(status);
            em.merge(order);
        }
    }
}
