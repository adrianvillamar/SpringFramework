package com.adrian.repository; // Paquete donde se encuentra la clase

import java.util.List; // Importación de la clase List para manejar listas

import org.springframework.data.jpa.repository.JpaRepository; // Importación de JpaRepository, que proporciona métodos CRUD para la entidad

import com.adrian.model.Project; // Importación de la entidad Project
import com.adrian.model.User;    // Importación de la entidad User

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Este método busca proyectos cuyo nombre contenga una cadena parcial (partialName) y que el equipo del proyecto contenga al usuario dado.
    List<Project> findByNameContainingAndTeamContains(String partialName, User user); 

    // Este método encuentra proyectos en los que el usuario está en el equipo o es el propietario (dueño).
    List<Project> findByTeamContainingOrOwner(User user, User owner); 

    // MÉTODOS COMENTADOS ABAJO:

    // Este método estaba comentado. Si se descomenta, permite buscar todos los proyectos donde el dueño sea el usuario dado.
    //List<Project> findByOwner(User user); 

    // Consulta personalizada usando JPQL (Java Persistence Query Language).
    // Busca proyectos (p) que tengan un miembro en su equipo (team) igual al usuario dado.
    //@Query("SELECT p FROM Project p JOIN p.team t WHERE t = :user") 

    // Este método va acompañado de la consulta personalizada de arriba.
    // Si se descomenta, sirve para encontrar proyectos en los que el usuario participa en el equipo.
    //List<Project> findProjectByTeam(@Param("user") User user); 

    
}
