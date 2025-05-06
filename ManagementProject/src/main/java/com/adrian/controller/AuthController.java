package com.adrian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.config.JwtProvider;
import com.adrian.model.User;
import com.adrian.response.AuthResponse;
import com.adrian.repository.UserRepository;
import com.adrian.request.LoginRequest;
import com.adrian.services.CustomeUserDetailsImpl;
import com.adrian.services.SubscriptionService;

@RestController
@RequestMapping("/auth")  // Mapea las rutas bajo /auth
public class AuthController {

    @Autowired
    private UserRepository userRepository;  // Inyecta el repositorio de usuarios para interactuar con la base de datos

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inyecta el codificador de contraseñas para encriptar las contraseñas de los usuarios

    @Autowired
    private CustomeUserDetailsImpl customeUserDetails;  // Inyecta el servicio que carga detalles del usuario

    @Autowired
    private SubscriptionService subscriptionService;  // Inyecta el servicio de suscripción para manejar las suscripciones de los usuarios

    // Ruta para registrar un nuevo usuario
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        // Verifica si el correo electrónico del usuario ya está registrado
        User isUserExist = userRepository.findByEmail(user.getEmail());
        if (isUserExist != null) {
            throw new Exception("El correo electrónico ya está registrado.");  // Si el usuario ya existe, lanza una excepción
        }

        // Crea un nuevo usuario y lo guarda en la base de datos
        User createdUser = new User();
        createdUser.setFullName(user.getFullName());
        createdUser.setEmail(user.getEmail());  
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));  // La contraseña se encripta antes de guardar

        User savedUser = userRepository.save(createdUser);
        subscriptionService.createSubscription(savedUser);  // Crea una suscripción para el nuevo usuario

        // Realiza una autenticación automática después de que el usuario se registre
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genera un token JWT para el usuario autenticado
        String jwt = JwtProvider.generateToken(authentication);

        // Crea la respuesta con el mensaje y el token JWT
        AuthResponse res = new AuthResponse();
        res.setMessage("Signup successful");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);  // Retorna una respuesta HTTP 201 (creado)
    }

    // Ruta para iniciar sesión de un usuario
    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest) {
        
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Autenticación del usuario con el correo y la contraseña proporcionados
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genera un token JWT para el usuario autenticado
        String jwt = JwtProvider.generateToken(authentication);

        // Crea la respuesta con el mensaje y el token JWT
        AuthResponse res = new AuthResponse();
        res.setMessage("Signup successful");
        res.setJwt(jwt);

        return new ResponseEntity<>(res, HttpStatus.CREATED);  // Retorna una respuesta HTTP 201 (creado)
    }

    // Método privado para autenticar al usuario usando su correo y contraseña
    private Authentication authenticate(String username, String password) {
        
        // Carga los detalles del usuario desde la base de datos
        UserDetails userDetails = customeUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");  // Si el usuario no se encuentra, lanza una excepción
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");  // Si la contraseña no coincide, lanza una excepción
        }
        // Retorna un objeto de autenticación con las autoridades del usuario
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
