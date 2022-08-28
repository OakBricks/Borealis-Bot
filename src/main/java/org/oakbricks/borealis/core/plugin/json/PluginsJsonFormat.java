package org.oakbricks.borealis.core.plugin.json;

public class PluginsJsonFormat {
    public PluginsJsonFormat(int schemaVersion, String pluginName, String pluginId, String version, String mainClass, String[] authors, String[] dependencies) {
        this.schemaVersion = schemaVersion;
        this.pluginName = pluginName;
        this.pluginId = pluginId;
        this.version = version;
        this.mainClass = mainClass;
        this.authors = authors;
        this.dependencies = dependencies;
    }

    public PluginsJsonFormat() {
        this.schemaVersion = -1;
        this.pluginName = null;
        this.pluginId = null;
        this.version = null;
        this.mainClass = null;
        this.authors = null;
        this.dependencies = null;
    }

    int schemaVersion;
    String pluginName;
    String pluginId;
    String version;
    String mainClass;
    String[] authors;
    String[] dependencies;

    public String getPluginId() {
        return pluginId;
    }

    public String getMainClass() {
        return mainClass;
    }

    public int getSchemaVersion() {
        return schemaVersion;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getVersion() {
        return version;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String[] getDependencies() {
        return dependencies;
    }
}
