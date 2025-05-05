package com.adrian.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity  // Marca la clase como una entidad JPA, lo que significa que se mapea a una tabla en la base de datos
@Data  // Genera automáticamente los getters, setters, toString, hashCode, y equals
public class Issue {

    @Id  // Marca el campo 'id' como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor del id
    private Long id;  // Identificador único del problema (Issue)

    private String title;  // Título del problema (Issue)
    private String description;  // Descripción del problema (Issue)
    private String status;  // Estado del problema (Issue), por ejemplo: "abierto", "cerrado", etc.
    private Long projectID;
    private String priority;
    private LocalDate dueDate;  // Fecha de vencimiento del problema (Issue)
    private List<String> tags = new ArrayList<>();  // Etiquetas asociadas al problema (Issue)

    @ManyToOne  // Establece una relación de muchos a uno con la clase 'User'
    private User assignee;  // Usuario asignado a este problema (se refiere a la clase 'User')

    @JsonIgnore
    @ManyToOne
    private Project project;  // Proyecto al que pertenece este problema (se refiere a la clase 'Project')

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)  // Establece una relación de uno a muchos con la clase 'Comment'
    private List<Comment> comments = new ArrayList<>();  // Lista de comentarios asociados al problema (Issue)
}
