package com.example.SpringLoginManager.Services;

import com.example.SpringLoginManager.Entities.User;
import com.example.SpringLoginManager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> getUserByUsername(String user){
        return userRepository.findUserByUsername(user);
    }
}
