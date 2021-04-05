package builders.colonial.http.webrequesthandler.module.modules;

import builders.colonial.http.webrequesthandler.module.Module;

public class LoggerModule extends Module {
    @Override
    public void handleRequest(String s) {
        System.out.println(s);
    }
}
