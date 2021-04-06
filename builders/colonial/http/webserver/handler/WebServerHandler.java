package builders.colonial.http.webserver.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class WebServerHandler implements com.sun.net.httpserver.HttpHandler {
    public String HTML = "";
    @Override
    public void handle(HttpExchange t) throws IOException {
        t.sendResponseHeaders(200, HTML.length());
        OutputStream os = t.getResponseBody();
        os.write(HTML.getBytes());
        os.close();
    }
}