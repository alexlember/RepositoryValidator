package ru.lember.ConsistencyValidator.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Organization extends Entity {

    private String name;
    private String externalId;
}
