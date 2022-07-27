package org.oakbricks.borealis.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;

public @interface CommandOption {
    String name();
    String description();
    OptionType option();
    boolean isRequired() default true;
}
