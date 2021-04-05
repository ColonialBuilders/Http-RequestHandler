package builders.colonial.http.webrequesthandler.plugin;

import builders.colonial.http.webrequesthandler.Main;
import builders.colonial.http.webrequesthandler.module.Module;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginSetup {
    public static void LoadPlugins() {
        File PluginFolder = new File("plugins");
        if(!PluginFolder.isDirectory()) {
            PluginFolder.mkdir();
        }
        if(PluginFolder.listFiles() != null)
            try {
                for(File f : PluginFolder.listFiles()) {
            ZipInputStream jar = new ZipInputStream(new FileInputStream(f.getPath()));
            try {
                for (ZipEntry entry = jar.getNextEntry(); entry != null; entry = jar.getNextEntry())
                    if (entry.getName().endsWith(".class")) {
                        Class ClassFile = new URLClassLoader(
                                new URL[]{f.toURL()}).loadClass(entry.getName().replace("/", ".").replace(".class", ""));
                        if(ClassFile.getSuperclass().toString().contains("builders.colonial.http.webrequesthandler.module.Module")) {
                            Main.Modules.add((Module) ClassFile.newInstance());
                        }
                    }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}
