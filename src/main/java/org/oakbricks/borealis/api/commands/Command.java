package org.oakbricks.borealis.api.commands;

import kotlin.OptionalExpectation;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public abstract class Command extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        CommandEntry cmdEntry = this.getClass().getAnnotation(CommandEntry.class);

        if (!cmdEntry.name().isBlank()) {
            if (cmdEntry.name().equals(event.getName())) {
                onCommandRun(event);
            }
        }
    }

    private String name;

    public abstract void onCommandRun(SlashCommandInteractionEvent event);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
