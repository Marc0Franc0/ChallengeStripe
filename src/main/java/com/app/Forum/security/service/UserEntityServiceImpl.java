package com.app.Forum.security.service;

import com.app.Forum.repository.SubscriptionTypeRepository;
import com.app.Forum.security.dto.PersonalData;
import com.app.Forum.security.dto.SignUpDTO;
import com.app.Forum.security.dto.SignInDTO;
import com.app.Forum.security.jwt.JwtAuthenticationFilter;
import com.app.Forum.security.jwt.JwtTokenProvider;
import com.app.Forum.security.model.ERole;
import com.app.Forum.security.model.RoleEntity;
import com.app.Forum.security.model.UserEntity;
import com.app.Forum.security.repository.UserRepository;
import com.app.Forum.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class UserEntityServiceImpl implements UserEntityService{
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
     UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SubscriptionTypeRepository subscriptionTypeRepository;

    public UserEntity registerUser( SignUpDTO authDTO) {
        UserEntity userEntity =
                UserEntity.builder()
                        //nombre de usuario
                        .username(authDTO.getUsername())
                        //password encriptada
                        .password(passwordEncoder.encode(authDTO.getPassword()))
                        //roles del usuario el cual se establece como predeterminado USER
                        .roles(
                             Set.of(
                                     RoleEntity.builder()
                                             //Se le setea el rol de USER
                                     .name(ERole.valueOf("USER"))
                                     .build()))
                        //Otros datos
                        .personalData
                                (new PersonalData(
                                                authDTO.getFirstName(),
                                                authDTO.getLastName(),
                                                authDTO.getNewsletters()))
                        .build();


        return userRepository.save(userEntity);
    }

    //Método para la creación de un usuario de seguridad de Spring
    public User createUserSecurity(UserEntity userEntity) {
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                userEntity.getAuthorities());
    }

    public void authenticate(SignInDTO authDTO) {

        JwtAuthenticationFilter jwtAuthenticationFilter =
                new JwtAuthenticationFilter(jwtTokenProvider);
        //Se establece en el filtro creado el username y password del usuario que desea autenticarse
        jwtAuthenticationFilter.setUsernameParameter(authDTO.getUsername());
        jwtAuthenticationFilter.setPasswordParameter(authDTO.getPassword());

    }

    @Override
    public Optional<UserEntity> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    //Se actualiza la sub del usuario
    @Override
    public UserEntity updateUserSub(UserEntity user, Subscription subscription) {
        user.setSubscription(subscription);
        return userRepository.save(user);
    }

}
