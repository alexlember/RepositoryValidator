package ru.lember.ConsistencyValidator.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class Account extends Entity {

    private String organizationId;
    private BigDecimal balance;

}
