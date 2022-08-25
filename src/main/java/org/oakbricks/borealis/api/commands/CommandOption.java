package org.oakbricks.borealis.api.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.lang.annotation.Repeatable;

@Repeatable(CommandOptions.class)
public @interface CommandOption {
    String name();
    String description();
    OptionType option();
    boolean isRequired() default true;
}
