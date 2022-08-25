package org.oakbricks.borealis.api.events;

import java.util.HashMap;

public class RegistryEvent extends Event {
    public RegistryEvent() {

    }

    private HashMap<String, String> propertyHashMap = new HashMap<>();

    public RegistryEvent(RegistryEventTypes type, String id, String[] props) {
        if (type == RegistryEventTypes.ADD) {

        }
    }

    private void addProperty(String name, String entry) {
        propertyHashMap.put(name, entry);
    }

    public String getProperty(String name) {
        String outProp = (String) propertyHashMap.getOrDefault(name, "null");
        return String.format("%s:%s", name, outProp);
    }

    public void send(EventSender sender) {
        if (this.getProperty("type").equals("update")) {
            new RegistryEvent(RegistryEventTypes.UPDATE, sender.generateId(), sender.getEventProps());
        }
    }

    @Override
    protected String eventType() {
        return "registry";
    }

    public enum RegistryEventTypes {
        UPDATE,
        ADD,
        REMOVE,
        LOCK,
        UNLOCK
    }
}
