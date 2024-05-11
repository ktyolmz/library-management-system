package com.ktyolmz.library.service;

import com.ktyolmz.library.entity.User;
import com.ktyolmz.library.exception.UserException;
import com.ktyolmz.library.repository.UserRepository;
import com.ktyolmz.library.service.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestUserService {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private List<User> userList;

    @BeforeEach
    void setUp(){
        userList = List.of(
                createUser(1L, "kutay", "kutay@gmail.com", "1234567890", "abc sk"),
                createUser(2L, "merve", "merve@gmail.com", "0987654321", "cba sk")
        );
    }

    static User createUser(Long id, String name, String email, String telephone, String address) {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .telephone(telephone)
                .address(address)
                .build();
    }

    @Test
    void testAddUsers_withEmptyFields() {

        List<User> userListWithEmptyFields = List.of(User.builder().build());

        UserException exception = assertThrows(UserException.class, () -> {
            userService.addUsers(userListWithEmptyFields);
        });

        assertEquals("All fields must be filled for user: User(id=null, name=null, email=null, telephone=null, address=null)", exception.getMessage());
    }

    @Test
    void testAddUsers_telephoneExist() {
        when(userRepository.existsByTelephoneIn(any())).thenReturn(true);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.addUsers(userList);
        });

        assertEquals("User already exists!", exception.getMessage());
    }

    @Test
    void testAddUsers() {
        when(userRepository.existsByTelephoneIn(any())).thenReturn(false);
        when(userRepository.saveAll(userList)).thenReturn(userList);

        List<User> savedUsers = userService.addUsers(userList);

        assertEquals(2, savedUsers.size());
        assertTrue(savedUsers.stream().allMatch(user -> user.getId() != null));
    }


    @Test
    void testGetUsers() {
        when(userRepository.findAll()).thenReturn(userList);

        List<User> returnedUsers = userService.getUsers();

        assertEquals(userList.size(), returnedUsers.size());
        assertTrue(returnedUsers.containsAll(userList));
    }

    @Test
    void testGetUserById_userExists() {
        User expectedUser = userList.get(0);
        when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));

        User retrievedUser = userService.getUserById(1L);

        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    void testGetUserById_userNotExist() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getUserById(999L);
        });

        assertEquals("User Not Exist: 999", exception.getMessage());
    }

}
