package ru.lember.ConsistencyValidator.validator;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.lember.ConsistencyValidator.cache.CacheHelper;
import ru.lember.ConsistencyValidator.entity.Entity;
import ru.lember.ConsistencyValidator.lifecycle.ExitCode;
import ru.lember.ConsistencyValidator.lifecycle.ServerLifecycleController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Slf4j
@Service
public class ValidatorService {

    @Autowired
    private CacheHelper cacheHelper;

    @Autowired
    private ServerLifecycleController lifecycleController;

    private Map<Class<? extends Entity>, List<Validator<? super Entity>>> validators = new HashMap<>();

    @Autowired
    public ValidatorService(Validator[] validators) {
        Arrays.stream(validators).forEach(v -> {

            Class<? extends Entity> clazz = v.getClazz();

            if (!this.validators.containsKey(clazz)) {
                this.validators.put(clazz, new ArrayList<>());
            }

            this.validators.get(clazz).add(v);

        });
    }

    @PreDestroy
    private void preDestroy() {
        log.info("destroying");
    }

    @PostConstruct
    private void postConstruct() {
        try {

            validators.forEach((clazz, validators) -> cacheHelper
                    .getEntitiesByClass(clazz)
                    .forEach(entity -> validate(entity, validators)));

        } catch (IllegalStateException e) {
            lifecycleController.shutdownGracefully(e.getMessage(), ExitCode.CONTEXT_BUILT_ERROR.code);
        }

        log.info("initialized");

    }

    void validate(@NotNull Entity entity, @NotNull List<Validator<? super Entity>> validators) {
        validators.forEach(v -> {
            if (v.getIsValid().test(entity)) {
                log.debug("Entity: {} is valid. Validator: {}", entity, v);
            } else {
                String errorMessage = String.format("Entity: %s is not valid! Validator: %s", entity, v);
                if (v.getPolicy() == Policy.FAIL) {
                    throw new IllegalStateException(errorMessage);
                }
            }
        });
    }
}
