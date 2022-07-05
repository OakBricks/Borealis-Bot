package catalyststudios;

import com.sun.net.httpserver.HttpServer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

// token is
// ODYxMzg3ODk0MDUyMzU2MTM2.Ga74mp.FM76uGZyTxaMkDEzTeV-VfWJcyYAsrrAV7wtlM
public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(65479), 0);
        httpServer.start();
        args = new String[]{"ODYxMzg3ODk0MDUyMzU2MTM2.Ga74mp.FM76uGZyTxaMkDEzTeV-VfWJcyYAsrrAV7wtlM"};
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }
        // args[0] should be the token
        // We don't need any intents for this bot. Slash commands work without any intents!
        JDA jda = JDABuilder.createDefault(args[0])
                .addEventListeners(new Main())
                .setActivity(Activity.watching("your house"))
                .build();
        jda.upsertCommand("opt", "Gives a user a role if allowed by admins").addOption(OptionType.ROLE, "role", "An approved role that can be given to you", true).queue();
        jda.upsertCommand("verify", "If verified you can start chatting and interacting with the server").queue();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Bot started at " + LocalTime.now());
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
                System.err.printf("%s tried to get a role without the required permissions", member.getUser().getAsMention());
                event.reply("Unable to give role: Insufficient permissions").queue();
            }else if (role == guild.getPublicRole()) {
                System.out.printf("%s tried to give themselves @everyone!", member.getUser().getAsMention());
                event.reply("Unable to give role: Role is @everyone").setEphemeral(true).queue();
            } else if (role.getPosition() >= botHighestRole.getPosition() || role.getPosition() >= guild.getRoleByBot(bot.getId()).getPosition()) {
                // Oops, an error occurred
                System.out.println(member.getUser().getName() + " tried to get an unauthorized role!");
                event.reply("Unable to give role: Role is bot's role or above").setEphemeral(true).queue();
            } else if (!event.getMember().getRoles().contains(role)) {
                // Reply if the command ran successfully without error and the user got their role
                event.reply("Role applied successfully").setEphemeral(true).queue();
                guild.addRoleToMember(member, role).queue();
            } else {
                System.err.println("An unknown error occurred!");
                event.reply("An unknown error occurred!").setEphemeral(true).queue();
            }
        }

//        if (event.getName().equals("verify")) {
//            event.reply("hello").queue();
//        }

        if (event.getName().equals("verify")) {
            if (guild.getMember(UserSnowflake.fromId(member.getId())).getRoles().contains(guild.getRoleById("993678223487541418"))) {
                event.reply("You are already verified!").setEphemeral(true).queue();
            } else {
                event.reply("Press the green button to verify that you are a human.")
                        .addActionRow(Button.primary("button", generateButtonID())).setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        MessageChannel channel = event.getChannel();

        if (event.getComponentId().equals("button")) {
            guild.addRoleToMember(UserSnowflake.fromId(member.getId()), guild.getRoleById("993678223487541418")).queue();
            event.reply("You have been verified.").setEphemeral(true).queue();
//            event.reply(member.getAsMention() + " has been verified!").queue();
            channel.sendMessage(member.getAsMention() + " has been verified").queue();
        } else {
            return;
        }
    }

    // Stolen from https://github.com/OakBricks/Emerald lol
    public String generateButtonID() {
        Random random = new Random();
        String allowedCharacters = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder idStringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIntThatHelpsUsWithTheRandomChar = random.nextInt(allowedCharacters.length());
            char randomCharAtLast = allowedCharacters.charAt(randomIntThatHelpsUsWithTheRandomChar);

            idStringBuilder.append(randomCharAtLast);
        }

        return idStringBuilder.toString();
    }
}