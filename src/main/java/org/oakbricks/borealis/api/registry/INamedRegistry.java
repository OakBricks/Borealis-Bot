package org.oakbricks.borealis.api.registry;

import java.util.List;

public interface INamedRegistry<T> {
    void register(T toRegister, String name) throws RegistryException;

    T getFromRegistry(String name);

    List<T> getFromRegistry(T name);
}
