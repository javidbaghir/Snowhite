package com.Snowhite.service.impl;

import com.Snowhite.domain.User;
import com.Snowhite.exception.UserAlreadyExistException;
import com.Snowhite.exception.PasswordDoNotMatchException;
import com.Snowhite.repository.UserRepository;
import com.Snowhite.service.UserService;
import com.Snowhite.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Transactional
    @Override
    public User registerUser(User user) {

        User getUsername = userRepository.findByUsername(user.getUsername());

        if (getUsername != null) {
            throw new UserAlreadyExistException();
        } else {

            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                throw new PasswordDoNotMatchException();
            } else {

            String hashedPassword = passwordService.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);

                User saveUser = userRepository.save(user);

                return saveUser;
            }
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
