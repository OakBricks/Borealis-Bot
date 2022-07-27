package org.oakbricks.borealis.registry;

public interface IRegistry<T> {
    void register(T toRegister) throws RegistryException;

    T getFromRegistry(String name);

    T getFromRegistry(T name);
}
