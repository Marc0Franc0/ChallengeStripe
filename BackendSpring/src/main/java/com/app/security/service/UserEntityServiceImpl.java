package com.app.security.service;

import com.app.security.dto.*;
import com.app.security.jwt.JwtTokenProvider;
import com.app.security.model.ERole;
import com.app.security.model.RoleEntity;
import com.app.security.model.UserEntity;
import com.app.security.repository.UserRepository;
import com.app.model.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserEntityServiceImpl implements UserEntityService{
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;

    public UserEntity registerUser( SignUpDTO authDTO) {
        UserEntity userEntity = null;
        try {
            userEntity =
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
        } catch (Exception e) {
            log.error("No se pudo registar el usuario con los datos ingresados:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException
                    ("No se pudo registar el usuario con los datos ingresados: ".concat(e.getMessage()));
        }
    }
    //Método para la creación de un usuario de seguridad de Spring
    public User createUserSecurity(UserEntity userEntity) {
        try {
            return new User(
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.isEnabled(),
                    userEntity.isAccountNonExpired(),
                    userEntity.isCredentialsNonExpired(),
                    userEntity.isAccountNonLocked(),
                    userEntity.getAuthorities());
        }catch (Exception e){
            log.error("No se pudo crear User de la clase User de Spring Security:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException
                    ("No se pudo crear un User: ".concat(e.getMessage()));
        }
    }

    public LoginResponseDTO authenticate(SignInDTO request) {
        try{authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        String token=jwtTokenProvider.generateAccessToken(request.getUsername());
        return LoginResponseDTO.builder().token(token).build();}
        catch (Exception e){
            log.error("No se pudo iniciar sesión con los datos ingresados:, error: ".concat(e.getMessage()));
            e.printStackTrace();
        throw new RuntimeException("No se pudo iniciar sesión con los datos ingresados: "
                .concat(e.getMessage()));}
    }
    @Override
    public Optional<UserEntity> getUser(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
       try{
            return user;
       }catch (Exception e){
           log.error("No se pudo obtener los datos del usuario:, error: ".concat(e.getMessage()));
           e.printStackTrace();
           throw new RuntimeException("No se pudo obtener el usuario: ".concat(e.getMessage()));
       }

    }

    //Se actualiza la sub del usuario
    @Override
    public UserEntity updateUserSub(UserEntity user, Subscription subscription) {
        try {
            user.setSubscription(subscription);
        return userRepository.save(user);
        }catch (Exception e){
            log.error("No se pudo actualizar la suscripción del usuario:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException
                    ("No se pudo actualizar la suscripción del usuario: " +e.getMessage());
        }
    }

    @Override
    public UserDataDTO getUserData(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        try{
        return createUserData(user.get());
        }catch (Exception e){
            log.error("No se pudo obtener los datos del usuario:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException("No se pudo obtener los datos del usuario: ".concat(e.getMessage()));
        }
    }

    @Override
    public UserDataDTO createUserData(UserEntity user) {
        UserDataDTO userDataDTO;
        try{
        if(user.getSubscription()==null){

            userDataDTO= UserDataDTO
                    .builder()
                    .lastName(user.getPersonalData().getLastName())
                    .firstName(user.getPersonalData().getFirstName())
                    .subStatus("Sin suscripción activa")
                    .subType("")
                    .subEndDate("")
                    .username(user.getUsername())
                    .build();
        }else if((user.getSubscription().getEndDate()==null)){
            userDataDTO= UserDataDTO
                    .builder()
                    .lastName(user.getPersonalData().getLastName())
                    .firstName(user.getPersonalData().getFirstName())
                    .subStatus("Suscripción a pagar")
                    .subType(user.getSubscription().getSubscriptionType().getName())
                    .username(user.getUsername())
                    .subEndDate("")
                    .build();

        }else{
            String status;
            if(user.getSubscription().getActive()){
                status="Suscripción activa";
            }else{
                status="Sin suscripción activa";
            }
            userDataDTO= UserDataDTO
                    .builder()
                    .lastName(user.getPersonalData().getLastName())
                    .firstName(user.getPersonalData().getFirstName())
                    .subStatus(status)
                    .subType(user.getSubscription().getSubscriptionType().getName())
                    .username(user.getUsername())
                    .subEndDate(user.getSubscription().getEndDate().toString())
                    .build();
        }
        return userDataDTO;}
        catch (Exception e){
            log.error("No se pudo obtener los datos del usuario:, error: ".concat(e.getMessage()));
            e.printStackTrace();
            throw new RuntimeException("No se pudo obtener los datos del usuario: "
                    .concat(e.getMessage()));
        }
    }

}
