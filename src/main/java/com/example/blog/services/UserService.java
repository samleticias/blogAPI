package com.example.blog.services;

import com.example.blog.domain.entities.User;
import com.example.blog.domain.repositories.UserRepository;
import com.example.blog.rest.dtos.UserDTO;
import com.example.blog.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public void saveUser(User user){
        this.userRepository.save(user);
    }

    public User createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        this.saveUser(user);
        return user;
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
    }

}