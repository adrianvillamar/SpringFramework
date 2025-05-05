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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id; // Identificador único del proyecto
    private String name; // Nombre del proyecto
    private String description; // Descripción del proyecto
    private String category;

    private List<String> tags = new ArrayList<>(); // Etiquetas asociadas al proyecto

    @JsonIgnore
    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Chat chat;

    @ManyToOne
    private User owner; // Usuario asociado al proyecto

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue> issues = new ArrayList<>(); // Lista de issues del proyecto

    @ManyToMany
    private List<User> teams = new ArrayList<>(); // Lista de miembros del proyecto
}
