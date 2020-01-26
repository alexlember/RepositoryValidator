package ru.lember.ConsistencyValidator.validator;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lember.ConsistencyValidator.cache.CacheHelper;
import ru.lember.ConsistencyValidator.entity.Entity;
import ru.lember.ConsistencyValidator.lifecycle.ExitCode;
import ru.lember.ConsistencyValidator.lifecycle.ServerLifecycleController;
import ru.lember.ConsistencyValidator.validator.exceptions.FailedExceptionally;
import ru.lember.ConsistencyValidator.validator.exceptions.FailedSilently;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Slf4j
@Service
public class ValidatorServiceImpl implements ValidatorService {

    @Autowired
    private CacheHelper cacheHelper;

    @Autowired
    private ServerLifecycleController lifecycleController;

    private Map<Class<? extends Entity>, List<Validator<? super Entity>>> validators = new HashMap<>();

    @Autowired
    public ValidatorServiceImpl(Validator[] validators) {
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
        this.validate();
        log.info("initialized");

    }

    @Override
    public ValidationStatistics validate() {

        ValidatorService.Result result = Result.SUCCESS;
        Map<Class<? extends Entity>, Pair<Integer, Integer>> statictics = new HashMap<>();

        for (Class<? extends Entity> clazz : validators.keySet()) {

            ValidationStatistics statistic = validateByClass(clazz);
            if (statistic.getResult() == Result.FAILED_EXCEPTIONALLY
                    || statistic.getResult() == Result.FAILED_SILENTLY && result != Result.FAILED_EXCEPTIONALLY) {
                result = statistic.getResult();
            }

            statictics.put(clazz, statistic.getStatisticsByClass(clazz));
        }

        if (result == Result.FAILED_EXCEPTIONALLY) {
            lifecycleController.shutdownGracefully("Entity validation failed", ExitCode.CONTEXT_BUILT_ERROR.code);
        }

        return new ValidationStatistics(result, statictics);
    }

    @Override
    public ValidationStatistics validateByClass(@NotNull Class<? extends Entity> clazz) {
        Integer all = 0;
        Integer handled = 0;
            Collection<? extends Entity> entities = cacheHelper.getEntitiesByClass(clazz);
            all = entities.size();
            for (val e : entities) {
                try {
                    validateSingleEntity(e, validators.get(clazz));
                    handled++;
                } catch (FailedExceptionally ex) {
                    log.warn("validateByClass: {} failed exceptionally", clazz);
                } catch (FailedSilently ex) {
                    log.warn("validateByClass: {} failed silently", clazz);
                    return ValidationStatistics.ofFailedExceptionally(clazz, new Pair<>(handled, all));
                }
            }

        return handled == all
                ? ValidationStatistics.ofSuccess(clazz, new Pair<>(handled, all))
                : ValidationStatistics.ofFailedSilently(clazz, new Pair<>(handled, all));

    }

    private void validateSingleEntity(@NotNull Entity entity,
                                      @NotNull List<Validator<? super Entity>> validators)
            throws FailedExceptionally, FailedSilently {

        for (Validator<? super Entity> v : validators) {

            if (v.getIsValid().test(entity)) {
                log.debug("Entity: {} is valid. Validator: {}", entity, v);
            } else {
                String errorMessage = String.format("Entity: %s is not valid! Validator: %s", entity, v);
                log.error(errorMessage);
                if (v.getPolicy() == Policy.FAIL) {
                    throw new FailedExceptionally(errorMessage);
                } else {
                    throw new FailedSilently(errorMessage);
                }
            }
        }
    }
}
