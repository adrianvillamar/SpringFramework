package com.adrian.services;

import java.util.List;

import com.adrian.model.Chat;
import com.adrian.model.Project;
import com.adrian.model.User;

public interface ProjectService {
    
    Project createProject(Project project, User user) throws Exception; // Crea un nuevo proyecto asociado a un usuario

    List<Project> getProjectByTeam(User user, String category, String tag) throws Exception; // Obtiene proyectos del equipo del usuario filtrados por categoría y etiqueta

    Project getProjectById(Long projectId) throws Exception; // Obtiene un proyecto por su ID

    void deleteProject(Long projectId, Long userId) throws Exception; // Elimina un proyecto por su ID y el ID del usuario que lo elimina

    Project updateProject(Project updatedProject, Long id) throws Exception; // Actualiza un proyecto por su ID

    void addUserToProject(Long projectId, Long userId) throws Exception; // Agrega un usuario a un proyecto

    void removeUserFromProject(Long projectId, Long userId) throws Exception; // Elimina un usuario de un proyecto

    Chat getChatByProjectId(Long projectId) throws Exception; // Obtiene el chat asociado a un proyecto por su ID

    List<Project> searchProjects(String keyword, User user) throws Exception; // Busca proyectos por palabra clave dentro del equipo del usuario
}
