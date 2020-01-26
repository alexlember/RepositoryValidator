package ru.lember.ConsistencyValidator.lifecycle;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExitCode {

    UNEXPECTED_ERROR(1),
    CONTEXT_BUILT_ERROR(2);

    public int code;

}