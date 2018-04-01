package com.farelcigar.api.service.impl;

import com.farelcigar.api.domain.User;
import com.farelcigar.api.domain.dto.PasswordsDto;
import com.farelcigar.api.exception.EntityNotFoundException;
import com.farelcigar.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User createUser(User u) {

        User user = new User(
                u.getUsername(),
                u.getEmail(),
                bCryptPasswordEncoder.encode(u.getPassword()));

        logger.info("User with username [{}] created", user.getUsername());
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with id [" + id + "] not found")
                );
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException("User with username [" + username + "] not found")
                );
    }

    public boolean updatePassword(
            PasswordsDto passwordsDto,
            Authentication authentication) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return userRepository.findByUsername(authentication.getName())
                .map(user -> {
                    if (passwordEncoder.matches(passwordsDto.getOldPassword(), user.getPassword())) {
                        user.setPassword(bCryptPasswordEncoder.encode(passwordsDto.getNewPassword()));
                        logger.info("Changed password for user with username [{}]", user.getUsername());
                        userRepository.save(user);
                        return true;
                    }
                    return false;
                })
                .orElseThrow(() ->
                        new EntityNotFoundException("User with username [" + authentication.getName() + "] not found")
                );
    }

    public User getAuthUser(Authentication authentication) {
        if (authentication != null) {
            return userRepository.findByUsername(authentication.getName())
                    .orElseThrow(() ->
                            new EntityNotFoundException("User with username [" + authentication.getName() + "] not found")
                    );
        }
        return null;
    }
    public boolean isAuthenticated(Authentication authentication) {
        if (authentication != null) {
            UserDetails user = loadUserByUsername(authentication.getName());
            return user != null;
        }
        return false;
    }
}
