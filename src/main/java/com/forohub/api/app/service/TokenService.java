package com.forohub.api.app.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.forohub.api.domain.entity.User;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String apiSecret;

    public String generarToken(User usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foroHub")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", String.valueOf(usuario.getId()))
                    .withExpiresAt(generateExpirationToken())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
    }
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {

            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("foroHub")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null) {
            throw new ValidationException("Verifier Invalid");
        }
        return verifier.getSubject();
    }

    public String extractUserIdFromToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new ValidationException("Token is null or empty");
        }

        try {
            DecodedJWT decodedJWT = JWT.decode(token);

            // Imprimir todos los claims para depuración
            Map<String, Claim> claims = decodedJWT.getClaims();
            for (Map.Entry<String, Claim> entry : claims.entrySet()) {
                System.out.println("Claim: " + entry.getKey() + ", Value: " + entry.getValue().asString());
            }

            // Extraer el claim "id"
            Claim idClaim = decodedJWT.getClaim("id");
            if (idClaim != null && !idClaim.isNull()) {
                return idClaim.asString();  // Cambiar esto al tipo apropiado si es necesario, por ejemplo asInt(), asBoolean(), etc.
            } else {
                throw new ValidationException("Claim 'id' not found or is null");
            }
        } catch (JWTDecodeException exception) {
            throw new ValidationException("Invalid token: " + exception.getMessage());
        }
    }


    private Instant generateExpirationToken() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
