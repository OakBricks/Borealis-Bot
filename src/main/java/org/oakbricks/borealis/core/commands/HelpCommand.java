package org.oakbricks.borealis.core.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.oakbricks.borealis.api.commands.Command;
import org.oakbricks.borealis.api.commands.CommandEntry;

@CommandEntry(name = "help", description = "Provides information about installed commands.")
public class HelpCommand extends Command {
    @Override
    public void onCommandRun(SlashCommandInteractionEvent event) {
        event.reply("filler command").queue();
    }
}
