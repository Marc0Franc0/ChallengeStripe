package com.app.Forum.security.service;

import com.app.Forum.model.Subscription;
import com.app.Forum.security.dto.SignUpDTO;
import com.app.Forum.security.dto.SignInDTO;
import com.app.Forum.security.dto.UserDataDTO;
import com.app.Forum.security.model.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserEntityService {
    UserEntity registerUser(SignUpDTO authDTO);
    User createUserSecurity(UserEntity userEntity);
    void authenticate(SignInDTO authDTO);
    Optional<UserEntity> getUser(String username);
    UserEntity updateUserSub(UserEntity user, Subscription sub);

    UserDataDTO getUserData(String username);
    UserDataDTO createUserData(UserEntity user);
}
