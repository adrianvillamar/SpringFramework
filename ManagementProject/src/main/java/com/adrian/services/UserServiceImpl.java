package com.adrian.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.config.JwtProvider;
import com.adrian.model.User;
import com.adrian.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository; // Inyección del repositorio de usuarios

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt); // Extrae el email desde el token JWT
        return findUserByEmail(email); // Retorna el usuario encontrado por email
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email); // Busca usuario por email
        if (user == null) {
            throw new Exception("User not found"); // Lanza excepción si no existe
        }
        return user; // Retorna el usuario si se encuentra
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId); // Busca usuario por ID
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found"); // Lanza excepción si no existe
        }
        return optionalUser.get(); // Retorna el usuario encontrado
    }

    @Override
    public User updateUsersProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number); // Suma 'number' al tamaño de proyectos del usuario
        return userRepository.save(user); // Guarda y retorna el usuario actualizado
    }
}
