package com.example.springBootClient.controller;

import com.example.springBootClient.models.User;
import com.example.springBootClient.service.ServiceRestTemplateImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/rest")
public class ClientRestController {

        private ServiceRestTemplateImp serviceRestTemplate;

        @Autowired
        public ClientRestController(ServiceRestTemplateImp serviceRestTemplate) {
            this.serviceRestTemplate = serviceRestTemplate;
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET)
        public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            User user = this.serviceRestTemplate.getById(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
            HttpHeaders headers = new HttpHeaders();
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            this.serviceRestTemplate.add(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @RequestMapping(value = "/", method = RequestMethod.PUT)
        public ResponseEntity<User> updateUser(@RequestBody @Valid User user, UriComponentsBuilder builder) {
            HttpHeaders headers = new HttpHeaders();
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            this.serviceRestTemplate.update(user);
            return new ResponseEntity<>(user, headers, HttpStatus.OK);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
        public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
            User user = this.serviceRestTemplate.getById(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            this.serviceRestTemplate.remove(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping
        public ResponseEntity<List<User>> getAllUser() {
            List<User> allUsers = this.serviceRestTemplate.getAll();
            if (allUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
    }

