package com.adrian.config;

// Esta clase se usa para definir constantes relacionadas con el uso de JWT (JSON Web Token)
public class JwtConstant {

    // Nombre del header donde se espera que el token JWT esté presente en la solicitud HTTP
    public static final String JWT_HEADER = "Authorization";

    // Clave secreta usada para firmar y verificar el token JWT
    // Esta clave debe ser segura y tener al menos 32 caracteres para un buen nivel de protección
    public static final String SECRET_KEY = "mi_clave_super_secreta_que_debe_tener_al_menos_32_caracteres!";
}
