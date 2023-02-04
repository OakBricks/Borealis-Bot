package org.oakbricks.borealis.core;

import org.oakbricks.borealis.core.plugin.PluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Random;

public class PluginTester {
    private static final Logger LOGGER = LoggerFactory.getLogger("Plugin Tester");

    public static void main(String[] args) throws Exception {
        LOGGER.info("Running miscellaneous tests first!");
        LOGGER.debug("Event ID Testing: {}", generateEventId(generateRandomEventName()));

        File f = new File("plugins/BorealisBot-Plugin-Example-1.0-SNAPSHOT.jar");
        PluginLoader pl = new PluginLoader();

//        if (!f.exists() || !f.isDirectory()) {
//            LOGGER.warn("Creating plugins directory!");
//            f.mkdirs();
//        }
        LOGGER.info("Loading plugin...");
        pl.loadPlugin(f);
        LOGGER.info("Starting part 1 of plugin testing! Reading plugin.json!");

        LOGGER.info("Starting part 2 of plugin testing! Loading plugin!");
        pl.plugin.onLoad();
    }

    private static String generateEventId(String dummyEventName) {
        Random random = new Random();
        long idLong = random.nextLong(1000000000L, 9999999999L);
        return String.format("%s:%o", dummyEventName, idLong);
    }

    private static String generateRandomEventName() {
        String[] validEventNames = new String[]{
                "GenericEvent",
                "FooEvent",
                "BarEvent",
                "RegistryEvent",
                "BizEvent",
                "BotEvent",
                "ConfigurationEvent",
                "CommandEvent",
                "NullEvent"
        };
        Random random = new Random();
        int randInt = random.nextInt(0, validEventNames.length);

        return validEventNames[randInt];
    }
}
