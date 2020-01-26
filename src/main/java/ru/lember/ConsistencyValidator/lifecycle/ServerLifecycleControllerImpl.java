package ru.lember.ConsistencyValidator.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;

@Slf4j
@Component
public class ServerLifecycleControllerImpl implements ServerLifecycleController {

    private ConfigurableApplicationContext applicationContext;

    @Autowired
    public ServerLifecycleControllerImpl(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    private void postConstruct() {
        log.info("initialized");
    }

    @PreDestroy
    private void preDestroy() {
        log.info("destroying");
    }

    @Override
    public void shutdownGracefully(String message, Integer exitCode) {
        log.error("Server is stopping. Reason: {}", Optional.ofNullable(message).orElse("undefined"));
        System.exit(SpringApplication.exit(applicationContext, () -> Optional.ofNullable(exitCode).orElse(ExitCode.UNEXPECTED_ERROR.code)));
    }

}
