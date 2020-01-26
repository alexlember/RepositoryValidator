package ru.lember.ConsistencyValidator.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {

    private String name;
    private String surname;
    private Role role;
    private String organizationId;

}
