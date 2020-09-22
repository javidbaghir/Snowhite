package com.Snowhite.controller;

import com.Snowhite.domain.User;
import com.Snowhite.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/register")
@CrossOrigin
public class RegistrationController {

    static Logger log = LoggerFactory.getLogger(RegistrationController.class.getName());

    @Autowired
    private UserService userservice;

    @ApiOperation(value = "Register for User",
                  notes = "This method only registers the user",
                  response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {

        log.debug("User register post method is called");

        User registerUser = userservice.registerUser(user);

        log.info("New user registered " + registerUser);

        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }


}
