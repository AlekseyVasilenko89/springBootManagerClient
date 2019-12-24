package com.example.springBootClient.models;

import org.springframework.security.core.GrantedAuthority;

public class UserRoles implements GrantedAuthority {

    private Integer id;
    private String name;

    public UserRoles() {
    }

    public UserRoles(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
