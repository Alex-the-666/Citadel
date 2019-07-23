package com.github.alexthe666.citadel.server.entity;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gegy1000
 * @since 1.0.0
 */
public enum EntityDataHandler {
    INSTANCE;

    private Map<Entity, List<IEntityData<?>>> registeredEntityData = new WeakIdentityHashMap<>();

    /**
     * Registers an Extended Entity Data Manager to the given entity
     *
     * @param entity     the entity to add data to
     * @param entityData the data manager
     * @param <T>        the entity type
     */
    public <T extends Entity> void registerExtendedEntityData(T entity, IEntityData<T> entityData) {
        List<IEntityData<?>> registered = this.registeredEntityData.get(entity);
        if (registered == null) {
            registered = new ArrayList<>();
        }
        if (!registered.contains(entityData)) {
            registered.add(entityData);
        }
        this.registeredEntityData.put(entity, registered);
    }

    /**
     * @param entity     the entity
     * @param identifier the string identifier
     * @param <T>        the entity type
     * @return an IEntityData instance on the given entity with the given identifier
     */
    public <T extends Entity> IEntityData<T> getEntityData(T entity, String identifier) {
        List<IEntityData<T>> managers = this.getEntityData(entity);
        if (managers != null) {
            for (IEntityData manager : managers) {
                if (manager.getID().equals(identifier)) {
                    return manager;
                }
            }
        }
        return null;
    }

    /**
     * Get a list with all the registered data manager for the specified entity
     *
     * @param entity the entity instance
     * @param <T>    the entity type
     * @return a list with all the data managers, never null
     */
    public <T extends Entity> List<IEntityData<T>> getEntityData(T entity) {
        if (this.registeredEntityData.containsKey(entity)) {
            return (List<IEntityData<T>>) ((List<?>) this.registeredEntityData.get(entity));
        } else {
            return new ArrayList<>();
        }
    }
}