package com.green.greengram.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserInfoRoles extends User {
    private List<String> roles;
}
