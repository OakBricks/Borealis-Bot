package org.oakbricks.borealis.plugin;

public class PluginsJsonFormat {
    int schemaVersion;
    String pluginName;
    String pluginId;
    String version;
    String mainClass;
    String[] authors;
    String[] dependencies;
    /**
     * This tells if a mod is "secure"
     * If a mod is secure it doesn't have access to files, cant use mixin, ASM, or similar. (And unless <code>treatUnsecureAsUnsecure</code> is disabled, the bot will throw a warning listing unsecure mods)
     * Secure mods are recommended because they limit the possibilities of plugins that could be hijacked for use in raiding, DM spamming, and token-grabbing
     */
    boolean isSecure;
}
