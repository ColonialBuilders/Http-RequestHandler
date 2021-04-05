package builders.colonial.http.webrequesthandler.http.handler;

import builders.colonial.http.webrequesthandler.Main;
import builders.colonial.http.webrequesthandler.ResponseMode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
public class HttpHandler implements com.sun.net.httpserver.HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {//TODO add POST Request
            requestParamValue = (httpExchange).getRequestURI().toString().split("\\?")[1].split("=")[1];
        }

        //Handle the Response
        Main.RunModules(requestParamValue);
        if(Main.ResponseType != ResponseMode.NONE) {
            OutputStream outputStream = httpExchange.getResponseBody();
            String htmlResponse = "";
            if(Main.ResponseType == ResponseMode.CUSTOM) {
                htmlResponse = Main.Response;
            } else {
                htmlResponse = "Smart";//TODO add make a dynamic Response
            }
            httpExchange.sendResponseHeaders(200, htmlResponse.length());
            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}