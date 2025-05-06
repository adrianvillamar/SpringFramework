package com.adrian.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Identificador único de la suscripción
    private LocalDate subscriptionStartDate;  // Fecha de inicio de la suscripción
    private LocalDate getSubscriptionEndDate;  // Fecha de finalización de la suscripción
    private PlanType planType;
    private boolean isValid;

    @OneToOne
    private User user;
    
    
}
