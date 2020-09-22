package com.Snowhite.service;

import com.Snowhite.domain.User;

public interface UserService {

    User registerUser(User user);

    User findByUsername(String username);
}
