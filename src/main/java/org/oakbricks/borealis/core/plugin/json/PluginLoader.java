package org.oakbricks.borealis.core.plugin.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.oakbricks.borealis.core.Main;
import org.oakbricks.borealis.core.plugin.json.PluginsJsonFormat;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    private ClassLoader pluginLoader = ClassLoader.getSystemClassLoader();
    private static HashMap<PluginsJsonFormat, String> pluginJsonToIdMap = new HashMap<>();


    public static PluginsJsonFormat getPlugin(String pluginName) {
        for (Map.Entry<PluginsJsonFormat, String> entrySet : pluginJsonToIdMap.entrySet()) {
            if (entrySet.getValue().equals(pluginName)) {
                return entrySet.getKey();
            }
        }
        return new PluginsJsonFormat(-1, null, null, null, null, null, null);
    }

    public void loadPlugins(File pluginDir) throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, URISyntaxException {
        String loaderJson = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource("plugin.json").toURI()), StandardCharsets.UTF_8);
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        PluginsJsonFormat borealisInfoFromJson = GSON.fromJson(loaderJson, PluginsJsonFormat.class);
        URLClassLoader classLoader;
        List<URL> listOfURLs = new ArrayList<>();
        URL[] arrayOfURLs;

        Logger.info("\nPlugin {} (ID of {}) at {}:\n{}", borealisInfoFromJson.pluginName, borealisInfoFromJson.pluginId, null, loaderJson);

        for (File file : pluginDir.listFiles()) {
            if (!file.getName().endsWith(".jar") || file.isDirectory()) {
                return;
            }
            listOfURLs.add(file.toURI().toURL());
//            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
//            method.setAccessible(true);
//            method.invoke(pluginLoader, file);
//
//            for (Iterator<URL> it = pluginLoader.getResources("plugin.json").asIterator(); it.hasNext(); ) {
//                URL pluginDefURL = it.next();
//                File f = new File(pluginDefURL.getFile());
//                FileReader r = new FileReader(f);
//                PluginsJsonFormat plugin = GSON.fromJson(r, PluginsJsonFormat.class);
//
//                if (plugin.pluginId == null) {
//                    return;
//                }
//                Logger.debug("Found plugin {} with ID of {}", plugin.pluginName, plugin.pluginId);
//                pluginJsonToIdMap.put(plugin, plugin.pluginId);
//            }

        }

        classLoader = new URLClassLoader(listOfURLs.toArray(URL[]::new));

        for (Iterator<URL> it = classLoader.getResources("plugin.json").asIterator(); it.hasNext();) {
            URL pluginDefURL = it.next();
            File f = new File(pluginDefURL.getFile());
            FileReader r = new FileReader(f);
            PluginsJsonFormat plugin = GSON.fromJson(r, PluginsJsonFormat.class);

            if (plugin.pluginId == null) {
                return;
            }
            Logger.debug("Found plugin {} with ID of {}", plugin.pluginName, plugin.pluginId);
            pluginJsonToIdMap.put(plugin, plugin.pluginId);
        }
    }
}
