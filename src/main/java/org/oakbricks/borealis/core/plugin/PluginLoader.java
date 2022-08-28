package org.oakbricks.borealis.core.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.ApiStatus;
import org.oakbricks.borealis.core.Main;
import org.oakbricks.borealis.core.plugin.json.PluginsJsonFormat;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PluginLoader {
    public PluginLoader() {}
    private static HashMap<PluginsJsonFormat, String> pluginJsonToIdMap = new HashMap<>();



    @ApiStatus.Internal
    public List<PluginsJsonFormat> getPlugins() {
        List<PluginsJsonFormat> pluginsList = new ArrayList<>();

        pluginsList.addAll(pluginJsonToIdMap.keySet());

        return pluginsList;
    }

    public List<PluginsJsonFormat> getPlugins(String name) {
        List<PluginsJsonFormat> pluginsList = new ArrayList<>();

        for (PluginsJsonFormat p : pluginJsonToIdMap.keySet()) {
            if (p.getPluginName().equals(name)) {
                pluginsList.add(p);
            }
        }

        return pluginsList;
    }

    public PluginsJsonFormat getPlugin(String id) {
        for (PluginsJsonFormat p : pluginJsonToIdMap.keySet()) {
            if (Objects.equals(p.getPluginId(), id)) {
                return p;
            }
        }
        return new PluginsJsonFormat();
    }

    public void loadPlugins(File pluginDir) throws Exception {
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        PluginClassLoader classLoader;

        classLoader = new PluginClassLoader(this.getClass().getClassLoader());

        for (File file : pluginDir.listFiles()) {
            if (file.getName().endsWith(".jar") || !file.isDirectory()) {
                classLoader.addURL(file.toURI().toURL());
            }
        }

//        for (Enumeration<URL> it = classLoader.getResources("/plugin.json"); it.hasMoreElements(); ) {
//            URL u = it.nextElement();
//            String uF = u.getFile();
//            File f = new File(uF);
//            PluginsJsonFormat plugin = GSON.fromJson(FileUtils.readFileToString(f, StandardCharsets.UTF_8), PluginsJsonFormat.class);
//            f.createNewFile();
//
//            Logger.info(plugin.getPluginId());
//            pluginJsonToIdMap.put(plugin, plugin.getPluginId());
//        }


        char[] charBuffer = new char[0];
//        IOUtils.read(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream("/plugin.json"))), charBuffer);
//        System.out.printf("%s", charBuffer.toString());
        Logger.info(classLoader.findResource("/plugin.json"));

        for (PluginsJsonFormat plugin : pluginJsonToIdMap.keySet()) {
            Logger.info(plugin.getPluginId());
            Class<Plugin> mainClass = (Class<Plugin>) classLoader.loadClass(plugin.getMainClass());
            classLoader.resources("plugin.json");
            mainClass.getDeclaredConstructor().newInstance().onLoad();
            Logger.info("Loaded plugin {}!", plugin.getPluginId());
        }
    }

    private URL[] toUrlArray(Collection<URL> urlCollection) {
        URL[] arr = new URL[urlCollection.size()];
        for (int i = 0; i < urlCollection.size(); i++) {
            arr[i] = (URL) urlCollection.toArray()[i];
        }
        return arr;
    }
}
