package com.app.security.jwt;

import com.app.security.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*Esta clase es un filtro de autorización de Spring que se encarga de
    validar y procesar el token JWT en cada solicitud entrante.*/
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            // Solicitud entrante
            @NonNull HttpServletRequest request,
            // Respuesta saliente
            @NonNull HttpServletResponse response,
            // Mecanismo para invocar el siguiente filtro en la siguiente cadena de filtros
            @NonNull FilterChain filterChain
    ) throws IOException {
        String headerAuthorization = request.getHeader("Authorization");
        //Validación de contenido del token

        try{   if (
            //Se espera que no sea nulo y comienze con "Bearer "
                        headerAuthorization != null &&
                        headerAuthorization.startsWith("Bearer")) {
            //substring que solo contiene solo el token
            String token = headerAuthorization.substring(7);

            //Se valida el token obtenido con el método creado en la clase JwtTokenProvider
            if(jwtTokenProvider.isTokenValid(token)){
                //Obtención del username que contiene el token con método de la clase JwtTokenProvider
                String username = jwtTokenProvider.getUsernameFromToken(token);
                /*
                 * Se crea un objeto userDetails el cual contendrá todos
                 * los detalles de nuestro username según el método loadUserByUsername implementado
                 * en la clase UserDetailsServiceImpl
                 */
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                //Se crea la autenticación de tipo UsernamePasswordAuthenticationToken
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());

                //Se establece la autenticación creada en el contexto de la aplicación
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
}
        // Permite que la solicitud continue hacia el siguiente filtro en
        // la cadena de filtro establcida en la configuración
        filterChain.doFilter(request, response);
    }  catch (Exception e){
            //Se crea el body de la respuesta de la solicitud
            Map<String, Object> error = new HashMap<>();
            //atributos del response
            error.put("Error message",e.getMessage());
            error.put("Status code",HttpStatus.INTERNAL_SERVER_ERROR.value());

            //Response
            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().flush();
        }
    }
}
