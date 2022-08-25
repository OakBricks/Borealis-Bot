package org.oakbricks.borealis.api.config;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Placeholder class for future use
 */
public abstract class ConfigProvider {
    private HashMap<String, String> configHashMap;

    String getConfigValue(String key) {
        return configHashMap.getOrDefault(key, "null:null");
    }
}