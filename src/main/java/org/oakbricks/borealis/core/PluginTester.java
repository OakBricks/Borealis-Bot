package org.oakbricks.borealis.core;

import org.oakbricks.borealis.core.plugin.json.PluginLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Random;

public class PluginTester {
    private static final Logger LOGGER = LoggerFactory.getLogger("Plugin Tester");

    public static void main(String[] args) throws IOException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, URISyntaxException {
        System.setProperty("illegal-access", "permit");
        LOGGER.info("Running miscellaneous tests first!");
        LOGGER.debug("Event ID Testing: {}", generateEventId(generateRandomEventName()));
        LOGGER.info("\nTesting out plugin loader!\nplease dont mind the log spam!");

        File f = new File("plugins");
        PluginLoader pl = new PluginLoader();

        if (!f.exists() || !f.isDirectory()) {
            LOGGER.warn("Creating plugins directory!");
            f.mkdirs();
        }

        LOGGER.info("Starting part 1 of plugin testing! Reading plugin.json!");
        LOGGER.info("Also printing out the contents of the borealis plugin.json as a smoke test");
        pl.loadPlugins(f);
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

        return "NullEvent";
    }
}
