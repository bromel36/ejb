package org.example.mywebapp;
import java.io.*;

import com.bromel.ejb.TestBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


@WebServlet("/test")
public class TestServlet extends HttpServlet {

    @EJB
    private TestBean testBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dbTime = testBean.testQuery();
        resp.getWriter().write(dbTime);
    }
}
