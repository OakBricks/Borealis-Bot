package org.oakbricks.borealis.api.events;

public class GenericEvent extends Event{
    public GenericEvent(Class<? extends EventSender> sender, long id) {
        this.sender = sender;
        this.id = id;
    }

    Class<? extends EventSender> sender;
    long id;

    @Override
    public String eventType() {
        return "generic";
    }
}
