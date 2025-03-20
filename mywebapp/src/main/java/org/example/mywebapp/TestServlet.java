package org.example.mywebapp;
import java.io.*;

import com.bromel.ejb.HelloRemote;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class TestServlet extends HttpServlet {
    @EJB
    private HelloRemote myBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String result = myBean.sayHello("User");
        response.getWriter().println("EJB Result: " + result);
    }
}