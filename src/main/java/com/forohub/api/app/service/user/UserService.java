package com.forohub.api.app.service.user;

import
        com.forohub.api.app.service.ResourceNotFoundException;
import com.forohub.api.app.service.TokenService;
import com.forohub.api.domain.dto.user.ShowUserDTO;
import com.forohub.api.domain.dto.user.UpdateUserDTO;
import com.forohub.api.domain.entity.User;
import com.forohub.api.infrastructure.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;


    public  ShowUserDTO show( String token){
        String id =   tokenService.extractUserIdFromToken(token.substring(7));

        try{

            User user = repository.getReferenceById(UUID.fromString(id));

            ShowUserDTO userDTO = new ShowUserDTO(user.getId().toString(), user.getName(), user.getEmail());
            return userDTO;
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("User no encontrado con ID: " + id);

        }

    }

    public List<User> all(){
        List<User> users = repository.findAll();
        return users;
    }


    public ShowUserDTO update(UUID id, UpdateUserDTO userDTO, String token){
        try {

            User user = repository.getReferenceById(id);
           verifyAuthorization(token,id);
            user.setName(userDTO.name());
            user.setEmail(userDTO.email());
            repository.save(user);

            ShowUserDTO updateUser = new ShowUserDTO(user.getId().toString(), user.getName(), user.getEmail());
            return updateUser;
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("User no encontrado con ID: " + id);

        }

    }

    public void delete(UUID id, String token){
        try{
            verifyAuthorization(token,id);
            User user = repository.getReferenceById(id);

            repository.delete(user);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("User no encontrado con ID: " + id);

        }
    }

    private void verifyAuthorization(String token, UUID id){
        String userID = tokenService.extractUserIdFromToken(token.substring(7));

        if(!userID.equals(id.toString())){
            throw new NoAuthorizedException();
        }

    }
}
