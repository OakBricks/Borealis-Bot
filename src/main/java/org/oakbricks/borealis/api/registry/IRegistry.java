package org.oakbricks.borealis.api.registry;

import java.util.List;

public interface IRegistry<T> {
    void register(T toRegister) throws RegistryException;

    T getFromRegistry(String name);

    List<T> getFromRegistry(T name);
}
