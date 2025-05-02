package com.adrian.request;

import lombok.Data;

@Data  // Lombok genera automáticamente los métodos getters, setters, toString, equals y hashCode
public class LoginRequest {

    // Atributo que representa el correo electrónico del usuario.
    private String email;

    // Atributo que representa la contraseña del usuario.
    private String password;
}
