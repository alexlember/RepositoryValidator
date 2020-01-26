package ru.lember.ConsistencyValidator.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.lember.ConsistencyValidator.entity.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class UserRoleValidator extends Validator<User> {


    public UserRoleValidator() {
        super(Policy.FAIL, User.class, user -> user.getRole() != null);
    }

    @PreDestroy
    private void preDestroy() {
        log.info("destroying");
    }

    @PostConstruct
    private void postConstruct() {
        log.info("initialized");
    }

    @Override
    public String toString() {
        return "UserRoleValidator. Role must not be empty.";
    }

}
