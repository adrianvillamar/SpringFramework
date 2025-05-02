package com.adrian.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adrian.model.User;
import com.adrian.repository.UserRepository;

@Service  // Marca la clase como un servicio de Spring, lo que permite la inyección de dependencias
public class CustomeUserDetailsImpl implements UserDetailsService {

    @Autowired  // Inyecta la instancia del repositorio de usuarios para acceder a los datos de usuario en la base de datos
    private UserRepository userRepository;

    // Método que carga los detalles del usuario según el nombre de usuario (en este caso, el correo electrónico)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // Busca al usuario en la base de datos usando el correo electrónico proporcionado
        User user = userRepository.findByEmail(username);

        // Si no se encuentra el usuario, lanza una excepción con un mensaje personalizado
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);  
        }

        // Aquí se pueden agregar roles y permisos específicos del usuario
        List<GrantedAuthority> authorities = new ArrayList<>();  // Lista de autoridades (roles) que el usuario tiene

        // Se devuelve un objeto `User` de Spring Security con el correo electrónico, la contraseña y las autoridades
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }
}
