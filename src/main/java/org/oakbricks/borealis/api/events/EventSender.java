package org.oakbricks.borealis.api.events;

public interface EventSender<T extends Event> {
    public T getEvent();

    void sendEvent();
}
