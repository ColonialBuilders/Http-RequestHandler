package builders.colonial.http.webrequesthandler;

import builders.colonial.http.webrequesthandler.http.handler.HttpHandler;
import builders.colonial.http.webrequesthandler.module.Module;
import builders.colonial.http.webrequesthandler.module.modules.LoggerModule;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static ResponseMode ResponseType;
    public static String Response;
    public static String Context;
    public static short Port;
    private static final ArrayList<Module> Modules = new ArrayList<Module>();
public static void main(String[] s) {
    try {
        LoadConfigFile();
    } catch (IOException e) {
        WriteConfig();
        try {
            LoadConfigFile();
        } catch (IOException e2) {
            System.out.println("Error with config");
        }
    }
    LoadModules();
    runServer();
}
    private static void WriteConfig() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("config.properties"));
            writer.write(
                    "#Server properties\n" +
                    "#ResponseModes are NONE, CUSTOM and SMART.\n" +
                    "#SMART is not done yet.\n" +
                    "#Response Only works if ResponseMode is CUSTOM.\n" +
                    "Context=req\n" +
                    "ResponseMode=custom\n" +
                    "Response=TestResponse\n" +
                    "Port=8001"
            );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void LoadModules() {
        Modules.add(new LoggerModule());
    }
    public static void RunModules(String s) {
    for (Module module : Modules) {
        module.handleRequest(s);
    }
    }
    private static void runServer() {
    HttpServer server = null;
    try {
        server = HttpServer.create(new InetSocketAddress("localhost", Port), 0);
    } catch (IOException e) {
        e.printStackTrace();
    }
    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    server.createContext("/" + Context, new HttpHandler());
    server.setExecutor(threadPoolExecutor);
    server.start();
    System.out.println("Server started on port " + Port);
}
    private static void LoadConfigFile() throws IOException {
        FileInputStream fis = new FileInputStream("config.properties");
        Properties prop = new Properties();
        prop.load(fis);
        fis.close();
        try {
            ResponseType = ResponseMode.valueOf(prop.getProperty("ResponseMode").toUpperCase());
        } catch (java.lang.IllegalArgumentException e) {
            ResponseType = ResponseMode.NONE;
            e.printStackTrace();
        }
        System.out.println("Running ResponseType is " + ResponseType);
        Response = prop.getProperty("Response");
        Context = prop.getProperty("Context");
        Port = Short.parseShort(prop.getProperty("Port"));
    }
}
