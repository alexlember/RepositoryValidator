package ru.lember.ConsistencyValidator.validator.exceptions;

public class FailedSilently extends Exception {
    public FailedSilently(String message) {
        super(message);
    }
}
