package com.app.security.service;

import com.app.model.Subscription;
import com.app.security.dto.LoginResponseDTO;
import com.app.security.dto.SignUpDTO;
import com.app.security.dto.SignInDTO;
import com.app.security.dto.UserDataDTO;
import com.app.security.model.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserEntityService {
    UserEntity registerUser(SignUpDTO authDTO);
    User createUserSecurity(UserEntity userEntity);
    LoginResponseDTO authenticate(SignInDTO authDTO);
    Optional<UserEntity> getUser(String username);
    UserEntity updateUserSub(UserEntity user, Subscription sub);

    UserDataDTO getUserData(String username);
    UserDataDTO createUserData(UserEntity user);
}
