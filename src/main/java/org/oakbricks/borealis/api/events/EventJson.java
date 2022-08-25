package org.oakbricks.borealis.api.events;

public class EventJson {
    public EventJson(long id, String type, String[] properties) {
        this.id = id;
        this.type = type;
        this.properties = properties;
    }
    private long id;
    private String type;
    private String[] properties;

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String[] getProperties() {
        return properties;
    }
}
