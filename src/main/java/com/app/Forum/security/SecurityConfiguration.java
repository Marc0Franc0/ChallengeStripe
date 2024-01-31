package com.app.Forum.security;

import com.app.Forum.security.jwt.JwtAuthenticationFilter;
import com.app.Forum.security.jwt.JwtAuthorizationFilter;
import com.app.Forum.security.jwt.JwtTokenProvider;
import com.app.Forum.security.repository.UserRepository;
import com.app.Forum.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;
    /*
     * Se establece una cadena de filtros de seguridad en toda la aplicacion
     * Aquí se determinan los permisos según los roles de usuario para acceder a la
     * aplicación y demas configuraciones
     */
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http,
                                             AuthenticationManager authenticationManager)
    throws Exception{
        //Se utiliza el filtro de autenticación creado
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenProvider);
        //Se le establece un AutenticacionManager para que pueda funcionar correctamente el cual
        //es creado dentro de esta clase
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        //Se configura el endpoint para autenticarse
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");
        return http
                // Se deshabilita Cross-site request forgery
                .csrf(config->config.disable())
                // Permite la gestión de sesiones de tipo STATELESS osea que son sin estado
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Configuración de acceso a los endpoints
                .authorizeHttpRequests(
                        auth-> {
                            //Endpoint de documentación
                            auth.requestMatchers
                                    ("/swagger-ui.html","/v3/api-docs/**","/swagger-ui/**").permitAll();
                            auth.requestMatchers("/api/v1/auth/login","/api/v1/auth/register").permitAll();
                            auth.requestMatchers("/api/v1/subs/**").authenticated();
                            auth.requestMatchers("/api/v1/stripe/**").authenticated();
                            auth.requestMatchers("/api/v1/users/**").permitAll();
                            auth.anyRequest().authenticated();
                        })
                //Filtro creado el cual es necesario para autenticar un usuario con su username y password
                .addFilter(jwtAuthenticationFilter)
                //Filtro creado el cual es necesario para autorizar utilizando un token
                //Se ejecuta luego del filtro de autenticación
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    //Objeto el cual se encarga de la encriptación de contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //Este objeto se encarga de la administración de la autenticación de los usuarios
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity,PasswordEncoder passwordEncoder)
            throws Exception {
        //Creación de AuthenticationManagerBuilder
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        //Se establece el propiedades al objeto creado
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();

    }

    }
