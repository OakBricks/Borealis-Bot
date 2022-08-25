package org.oakbricks.borealis.api.registry.standard;

import org.oakbricks.borealis.api.registry.RegistryException;

import java.util.HashMap;
import java.util.List;

/**
 * Used for registries that don't automatically detect registered object names or registries that use HashMaps
 * @param <T>
 */
public interface IRegistry<T> {
    void register(T toRegister, String name) throws RegistryException;

    void register(T toRegister) throws RegistryException;

    T getFromRegistry(String name);

    List<T> getFromRegistry(T name);

    HashMap<String, T> getRegisteredObjects();
}
