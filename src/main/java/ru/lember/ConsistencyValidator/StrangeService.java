package ru.lember.ConsistencyValidator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class StrangeService {

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private AtomicInteger integer = new AtomicInteger();

    @PreDestroy
    private void preDestroy() {
        log.info("destroying");
    }

    @PostConstruct
    private void postConstruct() {
        log.info("initialized");

        executorService.scheduleAtFixedRate(
                () -> log.info("Counter: " + integer.incrementAndGet()),
                1000,
                1000,
                TimeUnit.MILLISECONDS);
    }
}
