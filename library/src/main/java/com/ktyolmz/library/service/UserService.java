package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

     List<User> addUsers(List<User> userList);

     List<User> getUsers();

     User getUserById(Long id);
}
