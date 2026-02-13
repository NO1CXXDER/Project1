package com.example.project1.service;

import com.example.project1.dto.SignupRequestDto;
import com.example.project1.entity.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public void processSignup(SignupRequestDto signupRequestDto) {
        userRepository.save(signupRequestDto.toUser(passwordEncoder));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("username '" + username + "' not found"));
    }
}