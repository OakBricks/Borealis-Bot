package org.oakbricks.borealis.api.registry.nameless;

import org.oakbricks.borealis.api.registry.RegistryException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Used for registries that only use a List to hold registered objects!
 * @param <T>
 */
public interface INamelessRegistry<T> {
    void register(T obj) throws RegistryException;

    // Remember to not register multiple objects of the same class!
    T getFromRegistry(T obj);

    List<T> getRegisteredObjects();
}
