package org.example.session;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/count")
public class CounterServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException {

        HttpSession session = req.getSession();
        if (session.isNew()) {
            session.setAttribute("counter", new AtomicInteger());
        }

        AtomicInteger counter = (AtomicInteger) session.getAttribute("counter");
        int now = counter.incrementAndGet();
        session.setAttribute("counter", counter);

        res.setContentType("text/plain; charset=utf-8");
        res.getWriter().write("count: " + now + ", host: " + InetAddress.getLocalHost().getHostName());
    }
}
