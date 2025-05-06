package com.adrian.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;


import com.adrian.model.PlanType;
import com.adrian.model.User;
import com.adrian.response.PaymentLinkResponse;
import com.adrian.services.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;


@RestController
@RequestMapping("/api/payment")  // Mapea las rutas bajo /api/payment
public class PaymentController {

    @Value("${razorpay.api.key}") 
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    @Autowired
    private UserService userService;  // Inyecta el servicio de usuario para manejar los usuarios

    @PostMapping("/{planType}")  // Ruta para crear un enlace de pago
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
        @RequestHeader("Authorization") String jwt,
        @PathVariable PlanType planType) 
        throws Exception {

            User user = userService.findUserProfileByJwt(jwt);  // Obtiene el usuario a partir del token de autorización
            int amount = 799*100;
            if(planType.equals(PlanType.ANNUALLY)) {
                amount = amount*12; 
                amount = (int)(amount*0.7); // Aplicar un 30% de descuento para el plan anual
            }

            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);  // Crea un cliente de Razorpay
            JSONObject paymentLinkRequest = new JSONObject();  // Crea un objeto JSON para la respuesta del enlace de pago
            paymentLinkRequest.put("currency", "INR");  // Establece la moneda del enlace de pago

            JSONObject customer = new JSONObject();  // Crea un objeto JSON para el cliente
            customer.put("name", user.getFullName());  // Establece el nombre del cliente
            customer.put("email", user.getEmail());  // Establece el correo electrónico del cliente
            paymentLinkRequest.put("customer", customer);  // Agrega el objeto cliente a la respuesta del enlace de pago

            JSONObject notify = new JSONObject();  // Crea un objeto JSON para la notificación
            notify.put("email", true);  // Habilita la notificación por correo electrónico
            paymentLinkRequest.put("notify", notify);  // Agrega el objeto de notificación a la respuesta del enlace de pago

            paymentLinkRequest.put("callback_url", "https://localhost:5173/upgrade_plan/succes?planType" + planType);  // Establece la URL de callback

            PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);  // Crea el enlace de pago
            String paymentLinkId = paymentLink.get("id");  // Obtiene el ID del enlace de pago
            String paymentLinkUrl = paymentLink.get("short_url");  // Obtiene la URL corta del enlace de pago

            PaymentLinkResponse res = new PaymentLinkResponse();  // Crea un objeto de respuesta del enlace de pago
            res.setPaymentLinkId(paymentLinkId);  // Establece el ID del enlace de pago en la respuesta
            res.setPaymentLinkUrl(paymentLinkUrl);  // Establece la URL del enlace de pago en la respuesta

            return new ResponseEntity<>(res, HttpStatus.CREATED);  // Devuelve la respuesta con un estado HTTP 200 OK

            }
}
