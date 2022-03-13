package com.example.demo.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CUSTOMER,ADMINISTRATOR,TRAVEL_AGENCY,MODERATOR;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
