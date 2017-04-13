package com.ahmadarif.jwt.service;

import com.ahmadarif.jwt.entity.User;
import com.ahmadarif.jwt.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by ARIF on 13-Apr-17.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            String usernameNotFound = String.format("Username '%s' tidak ditemukan.", username);
            logger.debug(usernameNotFound);
            throw new UsernameNotFoundException(usernameNotFound);
        } else {
            return user;
        }
    }

}