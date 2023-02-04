package org.oakbricks.borealis.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import org.jetbrains.annotations.NotNull;
import org.oakbricks.borealis.api.commands.Command;
import org.oakbricks.borealis.api.commands.CommandEntry;
import org.oakbricks.borealis.api.commands.CommandOption;
import org.oakbricks.borealis.api.commands.CommandOptions;
import org.oakbricks.borealis.api.registry.RegistryException;
import org.oakbricks.borealis.api.registry.standard.CommandRegistry;
import org.oakbricks.borealis.api.registry.standard.IRegistry;
import org.oakbricks.borealis.api.registry.RegistryHelper;
import org.oakbricks.borealis.core.commands.AboutCommand;
import org.oakbricks.borealis.core.config.ConfigFile;
import org.oakbricks.borealis.core.config.ConfigHelper;
import org.oakbricks.borealis.core.plugin.PluginLoader;
import org.oakbricks.borealis.core.plugin.json.PluginsJsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main extends ListenerAdapter {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final List<String> templateList = List.of("");
    private static ConfigFile config;
    private static PluginsJsonFormat loaderPlugin;
    private static FileWriter configFileWriter;
    // Don't do this in your plugins!
    public static IRegistry<Command> commandRegistry = new CommandRegistry();
    private static JDA jda;
    private static PluginLoader loader = new PluginLoader();

    public static void main(String[] args) throws LoginException, IOException, RegistryException {
//        System.setProperty("illegal-access", "permit");
        if (!ConfigHelper.configFile.exists()) {
            LOGGER.error("Config file not found, creating template!");
            if (ConfigHelper.configFile.createNewFile()) {
                configFileWriter = new FileWriter(ConfigHelper.configFile);
                String defaultSettings = GSON.toJson(new ConfigFile("BOT_TOKEN", false, templateList, "0"));
                configFileWriter.write(defaultSettings);
                configFileWriter.close();
                LOGGER.info("Template configuration file created, please edit the values in the configuration file as per the documentation");
            }
        }

        config = GSON.fromJson(new FileReader(ConfigHelper.configFile), ConfigFile.class);
        loaderPlugin = loader.getPlugin("loader");

        jda = JDABuilder.createDefault(config.getToken())
                .addEventListeners(new Main())
                .setActivity(Activity.watching("your house"))
                .build();

        for (PluginsJsonFormat plugin : loader.getPlugins()) {

        }

        RegistryHelper.register(new AboutCommand(loaderPlugin.getVersion()), commandRegistry, "");

        RegistryHelper.COMMANDS.getRegisteredObjects().forEach((name, cmd) -> {
            CommandEntry cmdEntry = cmd.getClass().getAnnotation(CommandEntry.class);
            CommandOptions cmdOptions = cmd.getClass().getAnnotation(CommandOptions.class);

            jda.addEventListener(cmd);

            CommandCreateAction cmdToBeMade = jda.upsertCommand(cmdEntry.name(), cmdEntry.description()).setGuildOnly(cmdEntry.isGuildOnly());

            if (cmdEntry.permissions().length != 0) {
                cmdToBeMade.setDefaultPermissions(DefaultMemberPermissions.enabledFor(cmdEntry.permissions())).queue();
            }

            if (cmdOptions.value().length != 0) {
                for (CommandOption cmdOpt : cmdOptions.value()) {
                    cmdToBeMade.addOption(cmdOpt.option(), cmdOpt.name(), cmdOpt.description());
                }
            }
            cmdToBeMade.queue();

        });
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("Bot started at {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("h:m:s a, d MMM uuuu")));
    }
}