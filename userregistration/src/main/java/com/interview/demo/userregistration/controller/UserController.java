package com.interview.demo.userregistration.controller;

import com.interview.demo.userregistration.dto.UserDTO;
import com.interview.demo.userregistration.entity.User;
import com.interview.demo.userregistration.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            String welcomeMessage = "Welcome " + userDTO.getUserName() +
                    " id " + userDTO.getId() +
                    " from city "+ userDTO.getCity();
            return new ResponseEntity<>(welcomeMessage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getUser/{userName}")
    public UserDTO getUser(@PathVariable String userName) {
        return userService.getUser(userName );
    }

    @GetMapping("/getMessage")
    public String getMessage() {
        return "Hello";
    }


}
