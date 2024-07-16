package com.forohub.api.infrastructure.controller;

import com.forohub.api.app.service.ResponseService;
import com.forohub.api.domain.dto.response.ResponseDTO;
import com.forohub.api.domain.dto.response.ShowResponseDTO;
import com.forohub.api.domain.dto.response.UpdateResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/response")
public class ResponseController {
    @Autowired
    private ResponseService responseService;
    @PostMapping
    @Transactional
    public ResponseEntity createResponse( @RequestBody @Valid ResponseDTO responseDTO,
                                         @RequestHeader("Authorization") String token){

     ShowResponseDTO response =  responseService.createResponse(responseDTO,token);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<List<ShowResponseDTO>> showAll(){
        List<ShowResponseDTO> responses = responseService.showAll();
        return ResponseEntity.ok(responses);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable UUID id,
                                 @RequestBody @Valid UpdateResponseDTO updateResponse,
                                 @RequestHeader("Authorization") String token){
        ShowResponseDTO updateR = responseService.updateResponse(id, updateResponse, token);
        return ResponseEntity.ok(updateR);
    }
}
