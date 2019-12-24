package com.example.springBootClient.service;

import com.example.springBootClient.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transient
public class ServiceRestTemplateImp implements ServiceRestTemplate, UserDetailsService {

    private static final String URI = "http://localhost:8080/admin/rest/";
    private RestTemplate restTemplate;
    private PasswordEncoder encoder;

    @Autowired
    public ServiceRestTemplateImp(RestTemplateBuilder builder, PasswordEncoder encoder) {
        this.restTemplate = builder.basicAuthentication("admin", "admin").build();
        this.encoder = encoder;
    }

    @Override
    public void add(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        restTemplate.postForEntity(URI, user, User.class);
    }

    @Override
    public List getAll() {
        ResponseEntity<User[]> users = restTemplate.getForEntity(URI, User[].class);
        return (List) users;
    }

    @Override
    public User getById(Integer id) {
        return restTemplate.getForObject(URI + "/{id}", User.class, id);
    }

    @Override
    public void update(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        restTemplate.put(URI, user, User.class);
    }

    @Override
    public void remove(User user) {
        restTemplate.delete(URI, user, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
