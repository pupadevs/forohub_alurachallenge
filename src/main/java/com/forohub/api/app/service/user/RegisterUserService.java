package com.forohub.api.app.service.user;

import com.forohub.api.app.service.DuplicateEntryException;
import com.forohub.api.domain.dto.user.UserDTO;
import com.forohub.api.domain.entity.User;
import com.forohub.api.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    public User createUser(UserDTO user){
        if (repository.existsByEmail(user.email())) {
            throw new DuplicateEntryException("Email address already exists");
        }
       var hashPass= passwordEncoder.encode(user.password());
      return  repository.save(new User(user.name(), user.email(),hashPass));

    }
}
