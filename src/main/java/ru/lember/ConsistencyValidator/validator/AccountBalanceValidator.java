package ru.lember.ConsistencyValidator.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.lember.ConsistencyValidator.entity.Account;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class AccountBalanceValidator extends Validator<Account> {


    public AccountBalanceValidator() {
        super(Policy.WARN, Account.class, account -> account.getBalance() != null);
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
        return "AccountBalanceValidator. Account balance must not be empty.";
    }

}
