package org.example.hello;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Inject
    HelloBean helloBean;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        resp.setContentType("text/plain; charset=utf-8");
        resp.getWriter().println(helloBean.sayHello("EAP8"));
    }
}
