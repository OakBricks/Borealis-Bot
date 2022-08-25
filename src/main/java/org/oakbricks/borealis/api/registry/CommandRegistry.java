package org.oakbricks.borealis.api.registry;

import org.oakbricks.borealis.api.commands.Command;
import org.oakbricks.borealis.api.commands.CommandEntry;
import org.oakbricks.borealis.api.events.Event;
import org.oakbricks.borealis.api.events.EventSender;
import org.oakbricks.borealis.api.events.RegistryEvent;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.List;

public class CommandRegistry implements IRegistry<Command>, EventSender<RegistryEvent> {
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
    public List<Command> getFromRegistry(Command name) {
        return null;
    }

    public HashMap<String, Command> getRegisteredCommands() {
        return registeredCommands;
    }

    @Override
    public RegistryEvent getEvent() {
        return null;
    }

    @Override
    public void sendEvent() {

    }
}
