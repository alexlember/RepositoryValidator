package ru.lember.ConsistencyValidator.validator;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ru.lember.ConsistencyValidator.cache.CacheFiller;
import ru.lember.ConsistencyValidator.cache.CacheHelper;
import ru.lember.ConsistencyValidator.cache.CacheHelperImpl;
import ru.lember.ConsistencyValidator.lifecycle.ServerLifecycleController;

@TestConfiguration
public class ValidatorServiceTestConfiguration {

    @Bean
    public ServerLifecycleController lifecycleController() {
        return Mockito.mock(ServerLifecycleController.class);
    }

    @Bean
    public CacheHelper cacheHelper() {
        return new CacheHelperImpl();
    }


    @Bean
    public CacheFiller cacheFiller() {
        return new CacheFiller();
    }

    @Bean
    public AccountBalanceValidator accountBalanceValidator() {
        return new AccountBalanceValidator();
    }

    @Bean
    public UserSurnameValidator userSurnameValidator() {
        return new UserSurnameValidator();
    }

    @Bean
    public UserRoleValidator userRoleValidator() {
        return new UserRoleValidator();
    }

    @Bean
    public ValidatorServiceImpl validatorService(final Validator[] validators) {
        return new ValidatorServiceImpl(validators);
    }

}
