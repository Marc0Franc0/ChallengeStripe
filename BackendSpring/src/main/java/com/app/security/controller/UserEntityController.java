package com.app.security.controller;

import com.app.security.dto.UserDataDTO;
import com.app.security.service.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/users")
public class UserEntityController {
    @Autowired
    private UserEntityService userEntityService;
    @Operation(summary = "Get user by username", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = UserDataDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username ){
        return ResponseEntity.status(HttpStatus.OK).body(userEntityService.getUserData(username));
    }
}
