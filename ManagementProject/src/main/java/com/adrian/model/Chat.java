package com.adrian.model;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // Identificador único del chat

    private String name; // Nombre del chat

    @OneToOne
    private Project project; // Proyecto asociado al chat

    @JsonIgnore
    @OneToMany(mappedBy = "chat",cascade = CascadeType.ALL, orphanRemoval = true) // Establece una relación de uno a muchos con la clase 'Message'
    private List<Message> messages; // Lista de mensajes en el chat

    @ManyToMany
    private List<User> users = new ArrayList<>(); // Lista de usuarios en el chat
    
    
}
