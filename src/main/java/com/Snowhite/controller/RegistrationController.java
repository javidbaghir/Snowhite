package com.Snowhite.controller;

import com.Snowhite.domain.Admin;
import com.Snowhite.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private AdminService adminservice;

    @ApiOperation(value = "Register for Admin",
                  notes = "This method only registers the admin",
                  response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<Admin> registerAdmin(@RequestBody @Valid Admin admin) {
        System.out.println("Admin " + admin);
        return new ResponseEntity<>(adminservice.registerAdmin(admin), HttpStatus.CREATED);
    }



}
