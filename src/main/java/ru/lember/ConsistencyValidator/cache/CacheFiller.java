package ru.lember.ConsistencyValidator.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.lember.ConsistencyValidator.entity.Account;
import ru.lember.ConsistencyValidator.entity.Organization;
import ru.lember.ConsistencyValidator.entity.Role;
import ru.lember.ConsistencyValidator.entity.User;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;

@Slf4j
@Component
public class CacheFiller {

    @Autowired
    private CacheHelper cacheHelper;

    @PostConstruct
    private void postConstruct() {
        log.info("initialized");

        User user1 = new User();
        user1.setEntityId("usr1");
        user1.setOrganizationId("org1");
        user1.setName("name1");
        user1.setSurname("surname1");
        user1.setRole(Role.CLEINT);

        User user2 = new User();
        user2.setEntityId("usr2");
        user2.setOrganizationId("org2");
        user2.setName("name2");
        user2.setSurname("surname2");

        User user3 = new User();
        user3.setEntityId("usr3");
        user3.setOrganizationId("org3");
        user3.setName("name3");
        user3.setSurname("surname3");
        user3.setRole(Role.DEALER);

        User user4 = new User();
        user4.setEntityId("usr4");
        user4.setOrganizationId("org3");
        user4.setName("name4");
        user4.setRole(Role.FINADMIN);

        cacheHelper.save(user1);
        cacheHelper.save(user2);
        cacheHelper.save(user3);
        cacheHelper.save(user4);

        Organization organization1 = new Organization();
        organization1.setEntityId("org1");
        organization1.setExternalId("orgExt1");
        organization1.setName("ooo 1");

        Organization organization2 = new Organization();
        organization2.setEntityId("org2");
        organization2.setExternalId("orgExt2");
        organization2.setName("ooo 2");

        Organization organization3 = new Organization();
        organization3.setEntityId("org3");
        organization3.setExternalId("orgExt3");
        organization3.setName("ooo 3");

        cacheHelper.save(organization1);
        cacheHelper.save(organization2);
        cacheHelper.save(organization3);

        Account account1 = new Account();
        account1.setEntityId("acc1");
        account1.setOrganizationId("org1");
        account1.setBalance(new BigDecimal("1000.0"));

        Account account2 = new Account();
        account2.setEntityId("acc2");
        account2.setOrganizationId("org2");
        account2.setBalance(new BigDecimal("1500.0"));

        Account account3 = new Account();
        account3.setEntityId("acc3");
        account3.setOrganizationId("org3");

        cacheHelper.save(account3);
        cacheHelper.save(account3);
        cacheHelper.save(account3);
    }

    @PreDestroy
    private void preDestroy() {
        log.info("destroying");
    }

}
