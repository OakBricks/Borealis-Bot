package org.oakbricks.borealis.api.registry.standard;

import org.oakbricks.borealis.api.commands.Command;
import org.oakbricks.borealis.api.commands.CommandEntry;
import org.oakbricks.borealis.api.events.EventSender;
import org.oakbricks.borealis.api.events.RegistryEvent;
import org.oakbricks.borealis.api.registry.RegistryException;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.List;

public class CommandRegistry implements IRegistry<Command>, EventSender<RegistryEvent> {
    private HashMap<String, Command> registeredCommands = new HashMap<>();
    private String[] event = new String[]{};

    @Override
    @Deprecated(since = "0.3.0-BETA")
    /**
     * THIS WILL NOT DO ANYTHING SPECIAL!!! PLEASE USE {@link #register(Command)} FOR GODS SAKE!!!!
     */
    public void register(Command toRegister, String name) throws RegistryException {
        register(toRegister);
    }

    @Override
    public void register(Command toRegister) throws RegistryException {
        Command cmd = toRegister;
        CommandEntry cmdEntry = cmd.getClass().getAnnotation(CommandEntry.class);
        String cmdName = cmdEntry.name();
        String cmdDesc = cmdEntry.description();

        if (cmdName.isBlank() || cmdDesc.isBlank()) {
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

    @Override
    public HashMap<String, Command> getRegisteredObjects() {
        return registeredCommands;
    }

    public HashMap<String, Command> getRegisteredCommands() {
        return registeredCommands;
    }

    @Override
    public void sendEvent(RegistryEvent event) {

        event.send(this);
    }

    @Override
    public void setEvent(String[] eventProps) {
        this.event = eventProps;
    }
}
