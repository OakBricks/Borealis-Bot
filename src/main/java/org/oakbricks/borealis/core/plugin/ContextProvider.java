package org.oakbricks.borealis.core.plugin;

import net.dv8tion.jda.api.JDA;
import org.oakbricks.borealis.core.plugin.json.PluginLoader;

import java.util.List;

public interface ContextProvider {
    JDA getJDA();
    PluginLoader getLoader();
    String getPlugin(String id);
    List<String> getPlugins(String name);
}
