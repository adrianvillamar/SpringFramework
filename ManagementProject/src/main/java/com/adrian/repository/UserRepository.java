package com.adrian.repository;

import com.adrian.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Este método permite buscar un usuario en la base de datos por su correo electrónico.
    // El método 'findByEmail' es generado automáticamente por Spring Data JPA
    // según el nombre del método, por lo que no es necesario escribir la implementación manualmente.
    User findByEmail(String email);  // Retorna un objeto 'User' que tiene el correo proporcionado
}
