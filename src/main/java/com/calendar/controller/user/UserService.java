package com.calendar.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isEmailTaken(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void registerUser(String name, String email, String password){
        User user = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        userRepository.save(user);
    }

    public User validateUser(String email, String password){
        return userRepository.findByEmailAndPassword(email, password)
                .orElse(null);
    }
}
