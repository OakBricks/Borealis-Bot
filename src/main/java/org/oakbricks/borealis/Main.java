package org.oakbricks.borealis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;
import org.oakbricks.borealis.config.ConfigFile;
import org.oakbricks.borealis.config.ConfigHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// token is
// ODYxMzg3ODk0MDUyMzU2MTM2.Ga74mp.FM76uGZyTxaMkDEzTeV-VfWJcyYAsrrAV7wtlM
public class Main extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
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

        jda.upsertCommand("giverole", "Gives a user a role if allowed by admins")
                .addOption(
                        OptionType.ROLE,
                        "role",
                        "An approved role that can be given to you",
                        true
                )
                .setGuildOnly(true).queue();
        jda.upsertCommand("verify", "If verified you can start chatting and interacting with the server")
                .setGuildOnly(true)
                .queue();
        jda.upsertCommand("ban", "Bans a user")
                .addOption(
                        OptionType.STRING,
                        "time",
                        "Amount of time to ban somebody, defaults to days (append h, d, w, m, or y for more choice on the amount of time for a ban)",
                        false
                )
                .setGuildOnly(true)
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(List.of(Permission.BAN_MEMBERS)))
                .queue();

        ArrayList<Permission> clearPermissions = new ArrayList<>();
        clearPermissions.add(Permission.MESSAGE_MANAGE);
        clearPermissions.add(Permission.MANAGE_CHANNEL);

        jda.upsertCommand("clear", "Clears a channel of its messages")
                .setDefaultPermissions(DefaultMemberPermissions.enabledFor(clearPermissions))
                .addOption(
                        OptionType.CHANNEL,
                        "channel",
                        "Channel to clean out",
                        true
                )
                .queue();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("Bot started at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("h:m:s a, d MMM uuuu")));
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        SelfUser bot = event.getJDA().getSelfUser();
        List<Role> botRoles = guild.getMember(UserSnowflake.fromId(bot.getId())).getRoles();
        Role botHighestRole = botRoles.get(botRoles.size() - 1);

        // Stop execution if the command is not /opt or any of the accepted commands
//        if (!event.getName().equals("opt") || !event.getName().equals("verify")) return;

        if (event.getName().equals("opt")) {
            Role role = event.getOption("role").getAsRole();
            if (event.getMember().getRoles().contains(role)) {
                // Reply if the user already has a role
                event.reply("You already have this role!").setEphemeral(true).queue();
            } else if (!member.hasPermission(Permission.MESSAGE_SEND)) {
                LOGGER.warn("{} tried to get a role without the required permissions", member.getUser().getAsTag());
                event.reply("Unable to give role: Insufficient permissions").setEphemeral(true).queue();
            }else if (role == guild.getPublicRole()) {
                LOGGER.info("{} tried to give themselves @everyone!", member.getUser().getAsTag());
                event.reply("Unable to give role: Role is @everyone").setEphemeral(true).queue();
            } else if (role.getPosition() >= botHighestRole.getPosition() || role.getPosition() >= guild.getRoleByBot(bot.getId()).getPosition()) {
                LOGGER.warn("{} tried to get an unauthorized role!", member.getUser().getAsTag());
                event.reply("Unable to give role: Role is bot's role or above").setEphemeral(true).queue();
            } else if (!event.getMember().getRoles().contains(role)) {
                // Reply if the command ran successfully without error and the user got their role
                LOGGER.info("{} got the role {} successfully!", member.getUser().getAsTag(), role.getName());
                event.reply("Role applied successfully").setEphemeral(true).queue();
                guild.addRoleToMember(member, role).queue();
            } else {
                LOGGER.error("An unknown error occurred with the request for {}!", member.getUser().getAsTag());
                event.reply("An unknown error occurred!").setEphemeral(true).queue();
            }
        }

        if (event.getName().equals("verify")) {
            if (config.getVerifyRole().isEmpty()) {
                event.reply("This command has been disabled by server administrators").setEphemeral(true).queue();
            } else if (member.getRoles().contains(event.getGuild().getRoleById(config.getVerifyRole()))) {
                event.reply("You are already verified!").setEphemeral(true).queue();
            } else {
                event.reply("Press the **rojo** button to verify that you are a human.")
                        .addActionRow(Button.primary("button", generateButtonID()), Button.danger("button2", generateButtonID())).setEphemeral(true).queue();
            }
        }

        if (event.getName().equals("clear")) {
            TextChannel textChannel = event.getOption("channel").getAsTextChannel();

            textChannel.createCopy().queue();
            textChannel.delete().queue();
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        TextChannel channel = event.getTextChannel();

        if (event.getComponentId().equals("button")) {
            event.reply("Totally verified").setEphemeral(true).queue();
        } else if (event.getComponentId().equals("button2")) {
            guild.addRoleToMember(UserSnowflake.fromId(member.getId()), guild.getRoleById("992867609252999299")).queue();
            event.reply("You have been verified.").setEphemeral(true).queue();
            channel.sendMessage(member.getAsMention() + " has been verified").queue();
        }
    }

    // Stolen from https://github.com/OakBricks/Emerald lol
    public String generateButtonID() {
        Random random = new Random();
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder idStringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIntThatHelpsUsWithTheRandomChar = random.nextInt(allowedCharacters.length());
            char randomCharAtLast = allowedCharacters.charAt(randomIntThatHelpsUsWithTheRandomChar);

            idStringBuilder.append(randomCharAtLast);
        }

        return idStringBuilder.toString();
    }
}