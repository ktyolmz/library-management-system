package com.ktyolmz.library.controller;

import com.ktyolmz.library.entity.User;
import com.ktyolmz.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public List<User> addUsers(@RequestBody List<User> userList) {
        return userService.addUsers(userList);
    }

    @GetMapping
    @Transactional
    public List<User> getUsers(){
        return userService.getUsers();
    }

}
