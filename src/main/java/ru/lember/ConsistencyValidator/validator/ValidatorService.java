package ru.lember.ConsistencyValidator.validator;

import javafx.util.Pair;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lember.ConsistencyValidator.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public interface ValidatorService {

    ValidationStatistics validate();

    /**
     * first - handled, second - all.
     */
    ValidationStatistics validateByClass(@NotNull Class<? extends Entity> clazz);

    @ToString
    class ValidationStatistics {

        @Getter
        private Result result;
        private Map<Class<? extends Entity>, Pair<Integer, Integer>> statistics;


        ValidationStatistics(@NotNull Result result, @NotNull Map<Class<? extends Entity>, Pair<Integer, Integer>> statistics) {
            this.result = result;
            this.statistics = statistics;
        }


        static ValidationStatistics ofSuccess(@NotNull Class<? extends Entity> clazz, Pair<Integer, Integer> statistic) {
            Map<Class<? extends Entity>, Pair<Integer, Integer>> statistics = new HashMap<>();
            statistics.put(clazz, statistic);
            return new ValidationStatistics(Result.SUCCESS, statistics);
        }

        static ValidationStatistics ofFailedSilently(@NotNull Class<? extends Entity> clazz, Pair<Integer, Integer> statistic) {
            Map<Class<? extends Entity>, Pair<Integer, Integer>> statistics = new HashMap<>();
            statistics.put(clazz, statistic);
            return new ValidationStatistics(Result.FAILED_SILENTLY, statistics);
        }

        static ValidationStatistics ofFailedExceptionally(@NotNull Class<? extends Entity> clazz, Pair<Integer, Integer> statistic) {
            Map<Class<? extends Entity>, Pair<Integer, Integer>> statistics = new HashMap<>();
            statistics.put(clazz, statistic);
            return new ValidationStatistics(Result.FAILED_EXCEPTIONALLY, statistics);
        }



        @Nullable
        Pair<Integer, Integer> getStatisticsByClass(@NotNull Class<? extends Entity> clazz) {
            return statistics.get(clazz);
        }

    }

    enum Result {
        SUCCESS,
        FAILED_SILENTLY,
        FAILED_EXCEPTIONALLY
    }
}
