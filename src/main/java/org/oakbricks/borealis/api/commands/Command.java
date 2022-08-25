package org.oakbricks.borealis.api.commands;

public abstract class Command {
    private String name;

    public abstract void onCommandRun(String commandName);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
