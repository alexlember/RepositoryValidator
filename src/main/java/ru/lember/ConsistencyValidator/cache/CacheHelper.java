package ru.lember.ConsistencyValidator.cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lember.ConsistencyValidator.entity.Entity;

import java.util.Collection;

public interface CacheHelper {

    <T extends Entity> void removeEntity(@NotNull final T entity);
    <T extends Entity> T save(@NotNull final T entity);
    <T extends Entity> @NotNull Collection<T> getEntitiesByClass(Class<T> type);
    <T extends Entity> @Nullable T getEntity(Class<T> type, String id);

}
