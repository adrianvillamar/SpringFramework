package com.adrian.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Lombok genera automáticamente los métodos getters, setters, toString, equals y hashCode
@NoArgsConstructor  // Lombok genera un constructor sin parámetros
@AllArgsConstructor // Lombok genera un constructor con parámetros para todos los campos
public class AuthResponse {

    // Atributo que almacenará el token JWT generado para el usuario autenticado.
    private String jwt;

    // Atributo que almacenará el mensaje de respuesta, por ejemplo, "Inicio de sesión exitoso".
    private String message;
}
