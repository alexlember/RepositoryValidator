package ru.lember.ConsistencyValidator.validator;

import lombok.Data;
import ru.lember.ConsistencyValidator.entity.Entity;

import java.util.function.Predicate;

@Data
public abstract class Validator<T extends Entity> {


    private Policy policy;
    private Class<T> clazz;
    private Predicate<T> isValid;

    Validator(Policy policy, Class<T> clazz, Predicate<T> isValid) {
        this.policy = policy;
        this.clazz = clazz;
        this.isValid = isValid;
    }
}
