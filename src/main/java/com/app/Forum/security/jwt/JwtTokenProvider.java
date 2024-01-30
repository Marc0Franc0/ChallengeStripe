package com.app.Forum.security.jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;

//Clase la cual brinda utilidades respecto a un token
@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    // Método para la generación de un token de acceso
    public String generateAccessToken(String username){
        //Se establecen las propiedades del token a crear y se retorna en el método como un string
        return Jwts.builder()
                //Username del token
                .setSubject(username)
                //Fecha de creación del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //Fecha de expiración del token
                .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(timeExpiration)))
                //Firma del totken
                .signWith(SignatureAlgorithm.HS256,getKey())
                .compact();

    }
    //Método el cual retorna la firma del token codificada
    private Key getKey() {
        //Se decodifica la secretKey
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // Se vuelve a codificar en una codificación util a la hora de generar un token y se retorna
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método para validar el token de acceso
    public boolean isTokenValid(String token){
        boolean isValid=false;
        try{
            //Obtención de contenido del token
            getAllClaims(token);
            isValid=true;
        }catch (Exception e){
            log.error("Token inválido, error: ".concat(e.getMessage()));
            e.printStackTrace();

        }
        return isValid;
    }
    //Método pra obtener todas las propiedades del un token
    private Claims getAllClaims(String token) {
        //El método parser se utiliza es el que se utiliza para leerlo
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para obtener el username del token
    public String getUsernameFromToken(String token){
        //Primero se obtienen de todos las propiedades del token
        //Luego Se obtiene y retorna el username
        return getAllClaims(token).getSubject();
    }

}
