package com.forohub.api.infrastructure.controller;


import com.forohub.api.app.service.user.RegisterUserService;
import com.forohub.api.domain.dto.user.UserDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {
    @Autowired
private RegisterUserService registerUserService;
    @PostMapping
    @Transactional
    public ResponseEntity registerUser(@RequestBody @Valid UserDTO user, UriComponentsBuilder uriComponentsBuilder){
        var data = registerUserService.createUser(user);
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(data.getId()).toUri();
        return ResponseEntity.created(url).body(data);
    }
}
