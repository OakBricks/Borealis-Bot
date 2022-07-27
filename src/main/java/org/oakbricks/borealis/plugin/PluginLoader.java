package org.oakbricks.borealis.plugin;

import java.io.File;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class PluginLoader {
    public PluginLoader() {}
    private ClassLoader pluginLoader = ClassLoader.getPlatformClassLoader();


    public void loadPlugins(File pluginDir) {
        for (File file : pluginDir.listFiles()) {
            if (!file.getName().endsWith(".jar")) {
                return;
            }

        }
    }
}
