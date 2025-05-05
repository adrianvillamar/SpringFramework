package com.adrian.services;

import com.adrian.model.User;

public interface UserService {

    User findUserProfileByJwt(String jwt) throws Exception; // Obtiene el perfil del usuario a partir del token JWT
    
    User findUserById(Long id) throws Exception; // Busca un usuario en la base de datos por su ID

    User findUserByEmail(String email) throws Exception; // Busca un usuario por su correo electrónico

    User updateUsersProjectSize(User user, int number); // Actualiza el número de proyectos del usuario
}
