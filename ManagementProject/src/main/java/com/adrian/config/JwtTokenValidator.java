package com.adrian.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.lang.NonNull; 

// Esta clase se encarga de validar el token JWT en cada solicitud HTTP entrante
public class JwtTokenValidator extends OncePerRequestFilter {

    // Este método se ejecuta para cada solicitud entrante
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                     @NonNull HttpServletResponse response, 
                                     @NonNull FilterChain filterChain) // El filterChain permite pasar la solicitud al siguiente filtro
            throws ServletException, IOException {

        // Obtiene el JWT del encabezado de la solicitud
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        // Si el token JWT existe en el encabezado
        if (jwt != null) {
            jwt = jwt.substring(7); // Quita el prefijo "Bearer " del token JWT

            try {
                // Crea una clave secreta a partir de la constante definida
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                
                // Analiza el JWT usando la clave secreta para verificar su autenticidad
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key) // Establece la clave secreta para la validación
                        .build() // Construye el parser
                        .parseClaimsJws(jwt) // Analiza el JWT
                        .getBody(); // Obtiene el cuerpo (claims) del JWT

                // Extrae el email y las autoridades del token
                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                // Convierte las autoridades (roles) del token en una lista de GrantedAuthority
                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                // Crea una autenticación a partir del email y las autoridades
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

                // Establece la autenticación en el contexto de seguridad para que sea accesible durante la solicitud
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // Si ocurre un error al procesar el token, lanza una excepción indicando que el token es inválido
                throw new BadCredentialsException("Invalid token...");
            }
        }

        // Pasa la solicitud al siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}
