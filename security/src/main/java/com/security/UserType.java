package com.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {

    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String value;
}
