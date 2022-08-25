package org.oakbricks.borealis.api.events;

import java.util.List;
import java.util.Random;

public interface EventSender<T extends Event> {
    String[] eventProps = new String[]{};

    void sendEvent(T event);

    default String generateId() {
        Random random = new Random();
        long idLong = random.nextLong(1000000000L, 9999999999L);
        return String.format("%s:%o", this.getClass().getSimpleName(), idLong);
    }

    void setEvent(String[] eventProps);

    default String[] getEventProps() {
        return eventProps;
    }
}
