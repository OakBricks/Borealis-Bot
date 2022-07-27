package org.oakbricks.borealis.registry;

import org.oakbricks.borealis.commands.Command;
import org.oakbricks.borealis.commands.CommandEntry;
import org.tinylog.Logger;

import java.util.Arrays;
import java.util.HashMap;

public class CommandRegistry implements IRegistry<Command> {
    private HashMap<String, Command> registeredCommands = new HashMap<>();

    @Override
    public void register(Command toRegister) throws RegistryException {
        Command cmd = toRegister;
        CommandEntry cmdEntry = cmd.getClass().getAnnotation(CommandEntry.class);
        String cmdName = cmdEntry.name();
        String cmdDesc = cmdEntry.description();

        if (!cmdName.isBlank() || cmdDesc.isBlank()) {
            throw new RegistryException("Cannot find the name and/or description for this command");
        }
        registeredCommands.put(cmdName, toRegister);
    }

    @Override
    public Command getFromRegistry(String name) {
        registeredCommands.forEach((commandName, command) -> {
            Logger.info("{}, {}", commandName, command.getClass().getName());
        });

        return null;
    }

    @Override
    public Command getFromRegistry(Command name) {
        return null;
    }

    public HashMap<String, Command> getRegisteredCommands() {
        return registeredCommands;
    }
}
