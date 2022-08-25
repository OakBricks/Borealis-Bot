package org.oakbricks.borealis.api.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigurationRegistry implements INamedRegistry<Class<?>> {
    HashMap<String, Class<?>> configurationRegistryMap = new HashMap<>();

    @Override
    public void register(Class<?> toRegister, String name) throws RegistryException {
        configurationRegistryMap.put(name, toRegister);
    }

    @Override
    public Class<?> getFromRegistry(String name) {
        AtomicReference<Class<?>> obj = new AtomicReference<>();
        configurationRegistryMap.forEach(((s, o) -> {
            if (s.equals(name)) {
                obj.set(o);
            }
        }));
        return obj.get();
    }

    @Override
    public List<Class<?>> getFromRegistry(Class<?> name) {
        List<Class<?>> objList = new ArrayList<>();
        configurationRegistryMap.forEach(((s, o) -> {
            if (o.equals(name)) {
                objList.add(o);
            }
        }));
        return objList;
    }
}
