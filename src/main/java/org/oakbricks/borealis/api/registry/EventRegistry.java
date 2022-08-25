package org.oakbricks.borealis.api.registry;

import org.oakbricks.borealis.api.events.EventListener;

import java.util.List;

public class EventRegistry implements IRegistry<EventListener> {

    @Override
    public void register(EventListener toRegister) throws RegistryException {

    }

    @Override
    public EventListener getFromRegistry(String name) {
        return null;
    }

    @Override
    public List<EventListener> getFromRegistry(EventListener name) {
        return null;
    }
}
