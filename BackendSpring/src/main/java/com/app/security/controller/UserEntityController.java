package com.app.security.controller;

import com.app.security.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/users")
public class UserEntityController {
    @Autowired
    private UserEntityService userEntityService;
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username ){
        return ResponseEntity.status(HttpStatus.OK).body(userEntityService.getUserData(username));
    }
}
