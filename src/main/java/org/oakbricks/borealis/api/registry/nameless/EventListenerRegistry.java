package org.oakbricks.borealis.api.registry.nameless;

import org.oakbricks.borealis.api.events.EventListener;
import org.oakbricks.borealis.api.registry.RegistryException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventListenerRegistry implements INamelessRegistry<EventListener> {
    List<EventListener> eventListenerList = new ArrayList<>();

    @Override
    public void register(EventListener obj) throws RegistryException {
        if (eventListenerList.contains(obj)) {
            throw new RegistryException("Cannot register two event listeners of the same class!");
        }
        eventListenerList.add(obj);
    }

    @Override
    public EventListener getFromRegistry(EventListener obj) {
        for (EventListener eventListener : eventListenerList) {
            if (eventListener.getClass() == obj.getClass()) {
                return obj;
            }
        }
        return null;
    }

    @Override
    public List<EventListener> getRegisteredObjects() {
        return eventListenerList;
    }
}
