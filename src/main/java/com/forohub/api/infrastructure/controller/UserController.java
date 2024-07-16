package com.forohub.api.infrastructure.controller;

import com.forohub.api.app.service.user.UserService;
import com.forohub.api.domain.dto.user.ShowUserDTO;
import com.forohub.api.domain.dto.user.UpdateUserDTO;
import com.forohub.api.domain.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
@Autowired
    private UserService service;

@GetMapping
    public ResponseEntity<ShowUserDTO> ShowUserById( @RequestHeader("Authorization") String token){
    ShowUserDTO user = service.show( token);
    return ResponseEntity.ok(user);
}

@PutMapping("/{id}")
@Transactional
    public ResponseEntity<ShowUserDTO> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserDTO userDTO,
             @RequestHeader("Authorization") String token
                                                    ){
    ShowUserDTO user = service.update(id, userDTO,token);
        return ResponseEntity.ok(user);

}
@GetMapping("/all")
public ResponseEntity showAllUsers(){
    List<User> users = service.all();
    List<ShowUserDTO> userDTO = users.stream().map(sh -> new ShowUserDTO(sh.getId().toString(), sh.getName(), sh.getUsername())).collect(Collectors.toList());
   return ResponseEntity.ok(userDTO);
}

@DeleteMapping("/{id}")
@Transactional
    public ResponseEntity deleteUser(@PathVariable UUID id, @RequestHeader("Authorization") String token){
    service.delete(id,token);
    return ResponseEntity.noContent().build();
}

}
