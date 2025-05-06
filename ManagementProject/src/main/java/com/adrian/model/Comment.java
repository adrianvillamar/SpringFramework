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
public class Comment {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id; // Identificador único del proyecto

    private String content;

    private LocalDateTime createdDateTime; // Fecha de creación del comentario

    @ManyToOne
    private Issue issue; // Proyecto asociado al chat

    @ManyToOne
    private User user; // Usuario que creó el comentario



}
