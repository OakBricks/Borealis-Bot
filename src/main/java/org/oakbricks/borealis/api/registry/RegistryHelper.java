package org.oakbricks.borealis.api.registry;

import org.oakbricks.borealis.api.commands.Command;
import org.oakbricks.borealis.api.events.EventListener;
import org.oakbricks.borealis.api.registry.nameless.EventListenerRegistry;
import org.oakbricks.borealis.api.registry.nameless.INamelessRegistry;
import org.oakbricks.borealis.api.registry.standard.CommandRegistry;
import org.oakbricks.borealis.api.registry.standard.ConfigurationRegistry;
import org.oakbricks.borealis.api.registry.standard.IRegistry;

public class RegistryHelper {
    public static IRegistry<Command> COMMANDS = new CommandRegistry();
    public static IRegistry<Class<?>> CONFIGURATION = new ConfigurationRegistry();
    public static INamelessRegistry<EventListener> EVENTLISTENERS = new EventListenerRegistry();

    public static void register(Object toRegister, IRegistry registryType, String name) throws RegistryException {
        if (registryType instanceof CommandRegistry) {
            registryType.register(toRegister);
            return;
        }
        registryType.register(toRegister, name);
    }

    public static void register(Object toRegister, INamelessRegistry registryType) throws RegistryException {
        registryType.register(toRegister);
    }


}
