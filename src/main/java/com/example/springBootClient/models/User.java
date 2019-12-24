package com.example.springBootClient.models;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public class User implements UserDetails {

    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private List<UserRoles> userRoles;

    public User(){
    };

    public User(Integer id, String username, String password, Integer age, List<UserRoles> userRoles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.userRoles = userRoles;
    }
//
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

//    question
    public UserRoles getAuthorities() {
        return userRoles;
    }

    public void serUserRoles() {
        this.userRoles = userRoles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
