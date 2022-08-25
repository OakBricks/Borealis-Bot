package org.oakbricks.borealis.core.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

public class PluginLoader {
    public PluginLoader() {}
    private ClassLoader pluginLoader = ClassLoader.getSystemClassLoader();
    private static HashMap<PluginsJsonFormat, String> pluginJsonToIdMap = new HashMap<>();


    public static PluginsJsonFormat getPlugin(String pluginName) {
        for (String s : pluginJsonToIdMap.values()) {
            if (s.equals(pluginName)) {

            }
        }
        return new PluginsJsonFormat("", "", "")
    }

    public void loadPlugins(File pluginDir) throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException, URISyntaxException {
        String loaderJson = FileUtils.readFileToString(new File(getClass().getClassLoader().getResource("plugin.json").toURI()), StandardCharsets.UTF_8);
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        PluginsJsonFormat borealisInfoFromJson = GSON.fromJson(loaderJson, PluginsJsonFormat.class);

        Logger.info("\nPlugin {} (ID of {}) at {}:\n{}", borealisInfoFromJson.pluginName, borealisInfoFromJson.pluginId, null, loaderJson);

        for (File file : pluginDir.listFiles()) {
            if (!file.getName().endsWith(".jar") || file.isDirectory()) {
                return;
            }

            Method method = null;
            if (pluginLoader instanceof URLClassLoader) {
                method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
            }
            assert method != null;
            method.invoke(pluginLoader, file.toURI());

            for (Iterator<URL> it = pluginLoader.getResources("plugin.json").asIterator(); it.hasNext(); ) {
                URL pluginDefURL = it.next();
                File f = new File(pluginDefURL.getFile());
                FileReader r = new FileReader(f);
                PluginsJsonFormat plugin = GSON.fromJson(r, PluginsJsonFormat.class);

                pluginJsonToIdMap.put(plugin, plugin.pluginId);
            }
        }
    }
}
