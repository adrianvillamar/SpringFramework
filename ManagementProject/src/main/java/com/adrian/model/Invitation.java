package com.adrian.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id; // Identificador único de la invitación
        
        private String email; // Correo electrónico del usuario invitado
        private String token; // Token de invitación
        private Long projectId; // ID del proyecto al que se envía la invitación
    
}
