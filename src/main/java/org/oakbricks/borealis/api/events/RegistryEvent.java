package org.oakbricks.borealis.api.events;

public class RegistryEvent extends Event {
    public RegistryEvent(RegistryEventTypes type, String id) {

    }

    @Override
    protected Events eventType() {
        return Events.REGISTRY_UPDATE;
    }

    public enum RegistryEventTypes {
        UPDATE,
        ADD,
        REMOVE,
        LOCK,
        UNLOCK
    }
}
