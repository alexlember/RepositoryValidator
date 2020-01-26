package ru.lember.ConsistencyValidator.cache;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.lember.ConsistencyValidator.entity.Entity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CacheHelperImpl implements CacheHelper {

    private Map<Class<? extends Entity>, Map<String, ? extends Entity>> cache = new ConcurrentHashMap<>();

    @PostConstruct
    private void postConstruct() {
        log.info("initialized");
    }

    @PreDestroy
    private void preDestroy() {
        log.info("destroying");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> type) {
        Map<String, ?> collection = cache.get(type);

        if (collection == null) {
            return Collections.emptyList();
        }

        return (List<T>) collection.values().stream().collect(Collectors.toList());
    }

    @Override
    public void removeEntity(@NotNull Entity entity) {
        final Class<? extends Entity> collectionClass = entity.getClass();

        Map<String, ?> collection = cache.get(collectionClass);

        if (collection != null) {
            collection.remove(entity.getEntityId());
            if (collection.isEmpty()) {
                cache.remove(collectionClass);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Entity> T save(@NotNull T entity) {
        final Class<? extends Entity> collectionClass = entity.getClass();

        Map<String, T> collection = (Map<String, T>) cache.get(collectionClass);

        if (collection == null) {
            collection = new ConcurrentHashMap<>();
            cache.put(collectionClass, collection);
        }

        return collection.put(entity.getEntityId(), entity);
    }

    @Override
    public <T extends Entity> @Nullable T getEntity(Class<T> type, String id) {
        Map<String, ?> collection = cache.get(type);
        if (collection == null) {
            return null;
        }

        return (T) collection.get(id);
    }


}
