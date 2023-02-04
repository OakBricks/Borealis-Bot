package org.oakbricks.borealis.core.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.oakbricks.borealis.api.commands.Command;
import org.oakbricks.borealis.api.commands.CommandEntry;

@CommandEntry(name = "about", description = "Shows information about the bot")
public class AboutCommand extends Command {
    public AboutCommand(String version) {
        this.versionString = version;
    }
    String versionString;

    @Override
    public void onCommandRun(SlashCommandInteractionEvent event) {
        event.reply("""
                BorealisBot {}
                
                Github: `github.com/OakBricks/Borealis-Bot/`
                
                Licensed under the MIT License!
                """.replace("{}", versionString))
                .setEphemeral(true)
                .queue();
    }
}
