package org.oakbricks.borealis.core.plugin;

public class PluginsJsonFormat {
    public PluginsJsonFormat(int schemaVersion, String pluginName, String pluginId, String version, String mainClass, String[] authors, String[] dependencies, boolean isSecure) {
        this.schemaVersion = schemaVersion;
        this.pluginName = pluginName;
        this.pluginId = pluginId;
        this.version = version;
        this.mainClass = mainClass;
        this.authors = authors;
        this.dependencies = dependencies;
    }

    int schemaVersion;
    String pluginName;
    String pluginId;
    String version;
    String mainClass;
    String[] authors;
    String[] dependencies;
}
