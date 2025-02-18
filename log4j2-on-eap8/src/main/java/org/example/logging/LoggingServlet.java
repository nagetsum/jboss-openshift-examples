package org.example.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/log")
public class LoggingServlet extends HttpServlet {

    private static final Logger LOG = LogManager.getLogger(LoggingServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.info("Log4j2, Hello world!");
        resp.setContentType("text/plain; charset=utf-8");
        resp.getWriter().println("Log4j2, Hello world!");
    }
}
