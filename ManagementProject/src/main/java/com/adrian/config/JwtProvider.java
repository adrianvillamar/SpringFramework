package com.adrian.config;

import java.util.Date;
import javax.crypto.SecretKey;  // Importa la clase para manejar claves secretas de tipo HMAC

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Esta clase maneja la creación y validación de tokens JWT (JSON Web Token)
public class JwtProvider {

    // Clave secreta usada para firmar el JWT. Se obtiene desde la constante definida en JwtConstant.
    // La clave debe ser suficientemente larga (mínimo 32 caracteres) para ser segura.
    static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // Método que genera el token JWT
    public static String generateToken(Authentication auth) {
        return Jwts.builder() // Utiliza el builder de JJWT para construir el token
                .setIssuedAt(new Date()) // Establece la fecha de emisión del token
                .setExpiration(new Date(new Date().getTime() + 864000000)) // Establece la fecha de expiración (en milisegundos). Aquí se usa un valor de 10 días
                .claim("email", auth.getName()) // Agrega un "claim" personalizado, en este caso el email del usuario autenticado
                .signWith(key) // Firma el token usando la clave secreta
                .compact(); // Genera el token compactado
    }

    // Método que extrae el email del token JWT
    public static String getEmailFromToken(String jwt) {
        // Parseo del token JWT para obtener los claims (información) del cuerpo del token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // Utiliza la misma clave secreta para verificar la firma
                .build() // Construye el parser
                .parseClaimsJws(jwt) // Analiza el JWT
                .getBody(); // Obtiene el cuerpo del JWT (donde están los claims)
        
        // Extrae el valor del "claim" email
        return String.valueOf(claims.get("email"));
    }
}
