package org.oakbricks.borealis.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.oakbricks.borealis.core.config.ConfigFile;
import org.oakbricks.borealis.core.config.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main extends ListenerAdapter {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final List<String> templateList = List.of("");
    private static ConfigFile config;

    public static void main(String[] args) throws LoginException, IOException {
        if (!ConfigHelper.configFile.exists()) {
            LOGGER.error("Config file not found, creating template!");
            if (ConfigHelper.configFile.createNewFile()) {
                GSON.toJson(new ConfigFile("BOT_TOKEN", false, templateList, "0"));
                LOGGER.info("Template configuration file created, please edit the values in the configuration file as per the documentation");
            }
        }

        config = GSON.fromJson(new FileReader(ConfigHelper.configFile), ConfigFile.class);

        JDA jda = JDABuilder.createDefault(config.getToken())
                .addEventListeners(new Main())
                .setActivity(Activity.watching("your house"))
                .build();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("Bot started at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("h:m:s a, d MMM uuuu")));
    }
}