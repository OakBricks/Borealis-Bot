package org.oakbricks.borealis.api.registry;

import org.oakbricks.borealis.api.commands.Command;
import org.oakbricks.borealis.api.registry.standard.CommandRegistry;
import org.oakbricks.borealis.api.registry.standard.ConfigurationRegistry;
import org.oakbricks.borealis.api.registry.standard.IRegistry;

public class RegistryHelper {
    public static IRegistry<Command> COMMANDS = new CommandRegistry();
    public static IRegistry<Class<?>> CONFIGURATION = new ConfigurationRegistry();
    public static IRegistry<Command> EVENTS = new CommandRegistry();

    public static void register(Object toRegister, IRegistry registryType, String name) throws RegistryException {
        if (registryType instanceof CommandRegistry) {
            registryType.register(toRegister);
            return;
        }
        registryType.register(toRegister, name);
    }


}
