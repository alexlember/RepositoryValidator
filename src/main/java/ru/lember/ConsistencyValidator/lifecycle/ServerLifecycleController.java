package ru.lember.ConsistencyValidator.lifecycle;

import org.jetbrains.annotations.Nullable;

public interface ServerLifecycleController {

    void shutdownGracefully(@Nullable String message, @Nullable Integer exitCode);

}
