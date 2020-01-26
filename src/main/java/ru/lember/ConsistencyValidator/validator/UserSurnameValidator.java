package ru.lember.ConsistencyValidator.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.lember.ConsistencyValidator.entity.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class UserSurnameValidator extends Validator<User> {


    public UserSurnameValidator() {
        super(Policy.WARN, User.class, user -> !StringUtils.isEmpty(user.getSurname()));
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
        return "UserSurnameValidator. Surname must not be empty.";
    }
}
