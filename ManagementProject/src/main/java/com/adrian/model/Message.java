package com.adrian.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id; // Identificador único del proyecto

    private String content; // Contenido del mensaje

    private LocalDateTime createdAt; // Fecha de creación del mensaje

    @ManyToOne
    private Chat chat; // Proyecto asociado al chat

    @ManyToOne
    private User sender;


    
    
}
