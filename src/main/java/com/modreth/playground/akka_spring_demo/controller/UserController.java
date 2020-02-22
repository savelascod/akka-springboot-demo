package com.modreth.playground.akka_spring_demo.controller;

import com.modreth.playground.akka_spring_demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * {@link UserService} service instance
     */
    private final UserService userService;

    /**
     * Constructor with autowired dependencies
     * @param userService instance to be referenced
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * restful method Get
     * @return instance of {@link ResponseEntity}
     */
    @GetMapping
    public ResponseEntity<String> getUser() {
       return ResponseEntity.ok(userService.getUserInformation());
    }
}
