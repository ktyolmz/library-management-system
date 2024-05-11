package com.ktyolmz.library.service.serviceImpl;

import com.ktyolmz.library.entity.User;
import com.ktyolmz.library.exception.UserException;
import com.ktyolmz.library.repository.UserRepository;
import com.ktyolmz.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<User> addUsers(List<User> userList) {

        userList.forEach(user -> {
            if (isAnyFieldEmpty(user)) {
                throw new UserException("All fields must be filled for user: " + user);
            }
        });

        List<String> telephoneList = userList.stream()
                .map(User::getTelephone)
                .toList();

        if (userRepository.existsByTelephoneIn(telephoneList)) {
            throw new UserException("User already exists!");
        }

        return userRepository.saveAll(userList);
    }

    private boolean isAnyFieldEmpty(User user) {
        return user.getName() == null || user.getTelephone() == null || user.getEmail() == null || user.getAddress() == null;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User Not Exist: " + id));
    }

}
