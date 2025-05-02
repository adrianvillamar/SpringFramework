package com.adrian.config;

// Librerías necesarias
import java.util.Arrays;
import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

// Esta clase configura la seguridad de la aplicación
@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring Security
public class AppConfig {

    // Define el filtro principal de seguridad
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(Management -> Management
                // Define que la sesión será sin estado (ideal para APIs)
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(Authorize -> Authorize
                // Solo las rutas que empiezan con /api/ requieren autenticación
                .requestMatchers("/api/**").authenticated()
                // Todas las demás rutas están permitidas sin autenticación
                .anyRequest().permitAll())
            // Añade un filtro personalizado para validar el token JWT antes de la autenticación básica
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            // Desactiva la protección CSRF (útil para APIs REST)
            .csrf(csrf -> csrf.disable())
            // Habilita CORS con la configuración personalizada de abajo
            .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        // Devuelve la cadena de filtros construida
        return http.build();
    }

    // Configuración CORS: permite que otras aplicaciones (como frontend en React/Angular) se comuniquen con esta API
    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                // Dominios permitidos para consumir esta API
                cfg.setAllowedOrigins(Arrays.asList(
                    "http://localhost:3000", // React
                    "http://localhost:5173", // Vite
                    "http://localhost:4200"  // Angular
                ));
                // Permite todos los métodos (GET, POST, PUT, DELETE, etc.)
                cfg.setAllowedMethods(Collections.singletonList("*"));
                // Permite enviar cookies/autenticación
                cfg.setAllowCredentials(true);
                // Permite todos los headers
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                // Expone el header "Authorization" para que el frontend pueda acceder al token
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                // Cache de configuración durante 1 hora
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    // Bean para encriptar contraseñas con BCrypt
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para realizar llamadas HTTP desde el backend a otros servicios
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
