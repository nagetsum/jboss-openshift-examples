package org.example.hello;

import jakarta.ejb.Stateless;

@Stateless
public class HelloBean {
    public String sayHello(String s) {
        return "Hello, " + s;
    }
}
