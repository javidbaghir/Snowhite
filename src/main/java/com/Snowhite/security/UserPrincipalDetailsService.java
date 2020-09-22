package com.Snowhite.security;

import com.Snowhite.domain.User;
import com.Snowhite.exception.UserNotFoundException;
import com.Snowhite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);

        if (user != null) {
           UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
        throw new UserNotFoundException();
    }
}
