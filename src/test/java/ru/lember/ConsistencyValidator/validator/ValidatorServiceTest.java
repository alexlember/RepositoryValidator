package ru.lember.ConsistencyValidator.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.lember.ConsistencyValidator.lifecycle.ServerLifecycleController;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ValidatorServiceTestConfiguration.class)
class ValidatorServiceTest {

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ServerLifecycleController lifecycleController;

    @Test
    void test() {
        ValidatorService.ValidationStatistics statistics = validatorService.validate();

        Assertions.assertEquals(ValidatorService.Result.FAILED_EXCEPTIONALLY, statistics.getResult());

        // todo probably fix number of validations.
        Mockito.verify(lifecycleController, Mockito.times(2))
                .shutdownGracefully("Entity validation failed", 2);
    }
}
