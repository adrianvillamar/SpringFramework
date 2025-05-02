package com.adrian.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  // Anota la clase como una entidad JPA, lo que significa que se mapea a una tabla en la base de datos
@Data  // Genera automáticamente los getters, setters, toString, hashCode, y equals
@NoArgsConstructor  // Agrega un constructor sin argumentos para JPA
public class User {

    @Id  // Marca el campo 'id' como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.AUTO)  // Genera automáticamente el valor del id
    private Long id;  // Identificador único del usuario
    
    private String fullName;  // Nombre completo del usuario
    private String email;  // Correo electrónico del usuario
    private String password;  // Contraseña del usuario
    private int projectSize;  // Tamaño del proyecto asociado al usuario

    @JsonIgnore  // Ignora este campo durante la serialización JSON (no se enviará en respuestas JSON)
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL)  // Relación uno a muchos con 'Issue', donde el campo 'assignee' es la clave foránea en la clase 'Issue'
    private List<Issue> assignedIssues = new ArrayList<>();  // Lista de problemas asignados al usuario

}
