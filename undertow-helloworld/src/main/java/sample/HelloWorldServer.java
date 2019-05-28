package sample;

import io.undertow.Undertow;
import io.undertow.util.Headers;

public class HelloWorldServer {


    public static void main(final String[] args) {
        new HelloWorldServer().start();
    }

    private void start() {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(exchange ->  {
                    exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                    exchange.getResponseSender().send("Hello World");
                }).build();
        server.start();
    }
}
