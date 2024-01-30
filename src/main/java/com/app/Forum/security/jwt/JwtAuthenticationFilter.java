package com.app.Forum.security.jwt;

import com.app.Forum.security.model.UserEntity;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*Esta clase es un filtro de autenticación de Spring que se encarga de
    autenticar a los usuarios que soliciten utilizar la aplicación.*/
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    //Método el cual intenta la autenticación de un usuario


    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        UserEntity userEntity = null;
        String username = "";
        String password = "";
        try{
            // Se convierte en objeto UserEntity en json con los datos utilizando ObjectMapper
            userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            // Se obtienen el username y password del usuario y se almacenan
            username = userEntity.getUsername();
            password = userEntity.getPassword();
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Se crea la autenticación del usuario
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        //Se establece la autenticación creada del usuario en el contexto de la apliación
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    //Al haber una autenticación correcta se accede a este método
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // Se obtiene el objeto de tipo UserDetails con los datos del usuario autenticado
        User user = (User) authResult.getPrincipal();

        //Se genera el token del usuario autenticado correctamente
        //utilizando el método de la clase JwtTokenProvider
        String token = jwtTokenProvider.generateAccessToken(user.getUsername());

        //Se establece el header de authorization en la respuesta de la solicitud con el contenido del token
        response.setHeader("Authorization",token);

        //Se crea el body de la respuesta de la solicitud
        Map<String, Object> httpResponse = new HashMap<>();
        //token
        httpResponse.put("token",token);
        //Mensaje de información
        httpResponse.put("Message","Correct authentication");
        //Se envia el nombre de usuario autenticado
        httpResponse.put("Username",user.getUsername());

        //Se establece en el response el body creado
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        //Se establece el estado de la solicitud
        response.setStatus(HttpStatus.OK.value());
        //Se establece el tipo de contenido de la solicitud
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
