package com.adrian.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.model.PlanType;
import com.adrian.model.Subscription;
import com.adrian.model.User;
import com.adrian.repository.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository; 
    
    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12)); // Asignar un mes de suscripción
        subscription.setPlanType(PlanType.FREE); // Asignar un tipo de plan por defecto
        subscription.setValid(true); // Marcar la suscripción como válida
        return subscriptionRepository.save(subscription); // Guardar la suscripción en la base de datos 
    }

    @Override
    public Subscription getUsersSubscription(Long userId) throws Exception {
        Subscription subscription = subscriptionRepository.findByUserId(userId); // Buscar la suscripción del usuario por su ID
        if(!isInvalid(subscription)) { // Verificar si la suscripción es válida
            subscription.setPlanType(PlanType.FREE);
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12)); // Asignar 12 meses de suscripción si es FREE
            subscription.setSubscriptionStartDate(LocalDate.now()); // Actualizar la fecha de inicio de la suscripción
        }
        return subscriptionRepository.save(subscription); // Guardar la suscripción actualizada en la base de datos
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId); // Buscar la suscripción del usuario por su ID
        subscription.setPlanType(planType); // Actualizar el tipo de plan de la suscripción
        subscription.setSubscriptionStartDate(LocalDate.now()); // Actualizar la fecha de inicio de la suscripción
        if (planType.equals(PlanType.ANNUALLY)) {
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(12)); // Asignar un año de suscripción si es PREMIUM
        } else {
            subscription.setGetSubscriptionEndDate(LocalDate.now().plusMonths(1)); // Asignar un mes de suscripción si es FREE
        }
        return subscriptionRepository.save(subscription); // Guardar la suscripción actualizada en la base de datos
    }

    @Override
    public boolean isInvalid(Subscription subscription) {
        if (subscription.getPlanType().equals(PlanType.FREE)) {
            return true; // Si el tipo de plan es FREE, es inválido
        } 
        LocalDate endDate = subscription.getGetSubscriptionEndDate(); // Obtener la fecha de finalización de la suscripción
        LocalDate currentDate = LocalDate.now(); // Obtener la fecha actual
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate); // Verificar si la fecha de finalización es posterior a la fecha actual
    }
    
}
