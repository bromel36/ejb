package com.bromel.ejb.service;


import com.bromel.ejb.entities.Book;
import com.bromel.ejb.entities.Order;
import com.bromel.ejb.entities.OrderDetail;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Stateless
public class EmailService {
    @EJB
    private BookService bookService;

    private static final String EMAIL_FROM = "minhtien9a803062003@gmail.com";
    private static final String EMAIL_PASSWORD = "oppwbkfmjqqzlsli";

    public void sendOrderConfirmationEmail(String toEmail, Order order, List<OrderDetail> orderDetails) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Xác nhận đơn hàng #" + order.getId());

            Map<Integer, Book> bookMap = bookService.getBooksByOrderDetails(orderDetails);

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Chào bạn,\n\n");
            emailContent.append("Cảm ơn bạn đã đặt hàng tại BookShop! Dưới đây là thông tin đơn hàng của bạn:\n\n");
            emailContent.append("Mã đơn hàng: ").append(order.getId()).append("\n");
            emailContent.append("Ngày đặt: ").append(order.getOrderDate()).append("\n");
            emailContent.append("Thông tin giao hàng:\n");
            emailContent.append("  Tên: ").append(order.getReceiverName()).append("\n");
            emailContent.append("  Địa chỉ: ").append(order.getAddress()).append("\n");
            emailContent.append("  Số điện thoại: ").append(order.getReceiverPhone()).append("\n\n");
            emailContent.append("Chi tiết đơn hàng:\n");

            for (OrderDetail detail : orderDetails) {
                Book book = bookMap.get(detail.getBookId());
                String bookTitle = (book != null) ? book.getTitle() : "Sách không xác định";
                emailContent.append("  - ").append(bookTitle).append(" (x").append(detail.getQuantity())
                        .append("): ").append(detail.getPrice() * detail.getQuantity()).append(" VNĐ\n");
            }
            emailContent.append("\nTổng tiền: ").append(order.getTotalAmount()).append(" VNĐ\n");
            emailContent.append("Phương thức thanh toán: ").append(order.getPaymentMethod()).append("\n\n");
            emailContent.append("Chúng tôi sẽ xử lý đơn hàng sớm nhất có thể. Nếu có thắc mắc, vui lòng liên hệ qua email này.\n");
            emailContent.append("Trân trọng,\nBookShop Team");

            message.setText(emailContent.toString());
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
