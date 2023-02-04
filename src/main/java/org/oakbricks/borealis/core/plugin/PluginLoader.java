package org.oakbricks.borealis.core.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.ApiStatus;
import org.oakbricks.borealis.core.Main;
import org.oakbricks.borealis.core.plugin.json.PluginsJsonFormat;
import org.tinylog.Logger;

import java.io.*;
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
    private final HashMap<PluginsJsonFormat, String> pluginJsonToIdMap = new HashMap<>();
    public Plugin plugin;
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


    public void loadPlugin(File plugin) throws Exception {
        URL url = plugin.toURI().toURL();
        URL[] urls = new URL[]{url};
        ClassLoader cl = new URLClassLoader(urls);
        InputStream pluginFile = cl.getResourceAsStream("plugin.json");

        Class<?> mainClass = cl.loadClass("oakbricks.borealis.example.Init");
        this.plugin = (Plugin) mainClass.newInstance();
    }
}
