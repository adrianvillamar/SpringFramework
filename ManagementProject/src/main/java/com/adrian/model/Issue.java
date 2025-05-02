package com.adrian.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity  // Marca la clase como una entidad JPA, lo que significa que se mapea a una tabla en la base de datos
@Data  // Genera automáticamente los getters, setters, toString, hashCode, y equals
public class Issue {

    @Id  // Marca el campo 'id' como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor del id
    private Long id;  // Identificador único del problema (Issue)

    @ManyToOne  // Establece una relación de muchos a uno con la clase 'User'
    private User assignee;  // Usuario asignado a este problema (se refiere a la clase 'User')

}
