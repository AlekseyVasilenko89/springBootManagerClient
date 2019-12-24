package com.example.springBootClient.service;

import com.example.springBootClient.models.User;

import java.util.List;

public interface ServiceRestTemplate {

    void add(User user);

    List getAll();

    User getById(Integer id);

    void update(User user);

    void remove(User user);
}
