package com.adrian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.model.PlanType;
import com.adrian.model.Subscription;
import com.adrian.model.User;
import com.adrian.services.SubscriptionService;
import com.adrian.services.UserService;

@RestController
@RequestMapping("/api/subscription")  // Mapea las rutas bajo /api/subscription
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;  // Inyecta el servicio de suscripción para manejar las suscripciones de los usuarios

    @Autowired
    private UserService userService;  // Inyecta el servicio de usuario para manejar los usuarios
    
    @GetMapping("/user")  // Ruta para obtener la suscripción de un usuario
    public ResponseEntity<Subscription> getUserSubscription(
        @RequestHeader("Authorization") String jwt) 
        throws Exception {

            User user = userService.findUserProfileByJwt(jwt);  // Obtiene el usuario a partir del token de autorización
            Subscription subscription = subscriptionService.getUsersSubscription(user.getId());  // Obtiene la suscripción del usuario
            return new ResponseEntity<>(subscription, HttpStatus.OK);  // Devuelve la suscripción con un estado HTTP 200 OK
    }

    @PatchMapping("/upgrade") // Ruta para actualizar la suscripción de un usuario
    public ResponseEntity<Subscription> upgradeSubscription(
        @RequestHeader("Authorization") String jwt,
        @RequestParam PlanType planType) 
        throws Exception {

            User user = userService.findUserProfileByJwt(jwt);  // Obtiene el usuario a partir del token de autorización
            Subscription subscription = subscriptionService.upgradeSubscription(user.getId(), planType);  // Obtiene la suscripción del usuario
            return new ResponseEntity<>(subscription, HttpStatus.OK);  // Devuelve la suscripción con un estado HTTP 200 OK
    }


}
