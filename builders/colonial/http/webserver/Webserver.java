package builders.colonial.http.webserver;

import builders.colonial.http.webrequesthandler.Main;
import builders.colonial.http.webrequesthandler.module.Module;
import builders.colonial.http.webserver.handler.WebServerHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Webserver {
    public static void StartWebserver(HttpServer server) {
        File HTMLFolder = new File("html");
        if(!HTMLFolder.isDirectory()) {
            HTMLFolder.mkdir();
        }
        if(HTMLFolder.listFiles() != null) {
            for (File f : HTMLFolder.listFiles()) {
                if (f.getName().endsWith(".html")) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(f));
                        String html_text = "";
                        String string1 = "";
                        while ((string1 = br.readLine()) != null) {
                            html_text = html_text + string1;
                        }
                        WebServerHandler WSH = new WebServerHandler();
                        WSH.HTML = html_text;
                        if(f.getName().endsWith("index.html")) {
                            server.createContext("/", WSH);
                        } else {
                            server.createContext("/" + f.getName().replace(".html", ""), WSH);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        }
    }

