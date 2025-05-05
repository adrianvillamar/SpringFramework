package com.adrian.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.model.Chat;
import com.adrian.model.Project;
import com.adrian.model.User;
import com.adrian.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository; // Repositorio para acceder a los datos de proyectos

    @Autowired
    private UserService userService;             // Servicio para acceder a los datos de usuarios

    @Autowired
    private ChatService chatService;             // Servicio para manejar los chats de proyectos

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();                          // Crear instancia de proyecto
        createdProject.setName(project.getName());                       // Establecer nombre
        createdProject.setDescription(project.getDescription());         // Establecer descripción
        createdProject.setCategory(project.getCategory());               // Establecer categoría
        createdProject.setTags(project.getTags());                       // Establecer etiquetas
        createdProject.setOwner(user);                                   // Establecer propietario
        createdProject.getTeams().add(user);                             // Agregar al propietario al equipo

        Project savedProject = projectRepository.save(createdProject);   // Guardar proyecto

        Chat chat = new Chat();                                          // Crear nuevo chat
        chat.setProject(savedProject);                                   // Asociar chat al proyecto
        Chat projectChat = chatService.createChat(chat);                 // Crear chat en la base de datos

        savedProject.setChat(projectChat);                               // Asignar el chat al proyecto

        return savedProject;                                             // Retornar el proyecto guardado
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tag) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user); // Obtener proyectos del equipo o propios

        if (category != null) {
            projects = projects.stream()
                .filter(project -> project.getCategory().equals(category))                  // Filtrar por categoría
                .collect(Collectors.toList());
        }

        if (tag != null) {
            projects = projects.stream()
                .filter(project -> project.getTags().equals(tag))                           // Filtrar por etiqueta
                .collect(Collectors.toList());
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project> optionalProject = projectRepository.findById(projectId);           // Buscar proyecto por ID

        if (optionalProject.isEmpty()) {
            throw new Exception("Project not found");                                        // Lanzar excepción si no existe
        }

        return optionalProject.get();                                                        // Retornar proyecto encontrado
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId);                                                           // Verificar existencia del proyecto
        projectRepository.deleteById(projectId);                                             // Eliminar proyecto
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);                                                // Obtener proyecto original

        project.setName(updatedProject.getName());                                           // Actualizar nombre
        project.setDescription(updatedProject.getDescription());                             // Actualizar descripción
        project.setTags(updatedProject.getTags());                                           // Actualizar etiquetas

        return projectRepository.save(project);                                              // Guardar cambios
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);                                         // Obtener proyecto
        User user = userService.findUserById(userId);                                        // Obtener usuario

        if (!project.getTeams().contains(user)) {
            project.getChat().getUsers().add(user);                                          // Agregar usuario al chat
            project.getTeams().add(user);                                                    // Agregar usuario al equipo
        }

        projectRepository.save(project);                                                     // Guardar cambios
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);                                         // Obtener proyecto
        User user = userService.findUserById(userId);                                        // Obtener usuario

        if (project.getTeams().contains(user)) {
            project.getChat().getUsers().remove(user);                                       // Quitar usuario del chat
            project.getTeams().remove(user);                                                 // Quitar usuario del equipo
        }

        projectRepository.save(project);                                                     // Guardar cambios
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);                                         // Obtener proyecto
        return project.getChat();                                                            // Retornar su chat
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        return projectRepository.findByNameContainingAndTeamContains(keyword, user);         // Buscar por palabra clave y pertenencia al equipo
    }
}
