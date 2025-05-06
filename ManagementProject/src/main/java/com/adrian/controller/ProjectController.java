package com.adrian.controller; // Define el paquete donde se encuentra este controlador

// Importaciones necesarias para el funcionamiento del controlador
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.model.Chat; // Modelo de Chat
import com.adrian.model.Invitation;
import com.adrian.model.Project; // Modelo de Proyecto
import com.adrian.model.User; // Modelo de Usuario
import com.adrian.request.InviteRequest;
import com.adrian.response.MessageResponse; // Clase para respuestas con mensajes
import com.adrian.services.InvitationService;
import com.adrian.services.ProjectService; // Servicio para la lógica de negocio de proyectos
import com.adrian.services.UserService; // Servicio para la lógica de negocio de usuarios

@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/api/projects") // Define la ruta base para todas las peticiones relacionadas con proyectos
public class ProjectController {

    @Autowired
    private ProjectService projectService; // Inyección automática del servicio de proyectos

    @Autowired
    private UserService userService; // Inyección automática del servicio de usuarios

    @Autowired
    private InvitationService invitationService; // Inyección automática del servicio de invitaciones

    // Obtener todos los proyectos, con filtros opcionales de categoría y etiqueta
    @GetMapping 
    public ResponseEntity<List<Project>> getProjects(
        @RequestParam(required = false) String category, // Parámetro opcional de categoría
        @RequestParam(required = false) String tag, // Parámetro opcional de etiqueta
        @RequestHeader("Authorization") String jwt) throws Exception { // JWT en el encabezado para autenticación
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        List<Project> projects = projectService.getProjectByTeam(user, category, tag); // Obtiene los proyectos según el usuario y filtros
        return new ResponseEntity<>(projects, HttpStatus.OK); // Devuelve los proyectos con estado 200 OK
    }   

    // Obtener un proyecto por ID
    @GetMapping("/{projectId}") 
    public ResponseEntity<Project> getProjectById( 
        @PathVariable Long projectId, // Obtiene el ID del proyecto de la ruta
        @RequestHeader("Authorization") String jwt) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        Project project = projectService.getProjectById(projectId); // Busca el proyecto por ID
        return new ResponseEntity<>(project, HttpStatus.OK); // Devuelve el proyecto con estado 200 OK
    }    

    // Crear un nuevo proyecto
    @PostMapping() 
    public ResponseEntity<Project> createProject(
        @RequestHeader("Authorization") String jwt, // Encabezado con JWT
        @RequestBody Project project // Cuerpo de la solicitud con datos del nuevo proyecto
        ) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        Project createdProject = projectService.createProject(project, user); // Crea el proyecto asociado al usuario
        return new ResponseEntity<>(createdProject, HttpStatus.OK); // Devuelve el proyecto creado
    }

    // Actualizar un proyecto existente
    @PatchMapping("/{projectId}") 
    public ResponseEntity<Project> updateProject(
        @PathVariable Long projectId, // Obtiene el ID del proyecto de la ruta
        @RequestHeader("Authorization") String jwt, // JWT en el encabezado
        @RequestBody Project project // Cuerpo con los datos actualizados del proyecto
        ) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        Project updatedProject = projectService.updateProject(project, projectId); // Actualiza el proyecto
        return new ResponseEntity<>(updatedProject, HttpStatus.OK); // Devuelve el proyecto actualizado
    }

    // Eliminar un proyecto
    @DeleteMapping("/{projectId}") 
    public ResponseEntity<MessageResponse> deleteProject(
        @PathVariable Long projectId, // ID del proyecto a eliminar
        @RequestHeader("Authorization") String jwt // JWT del usuario
        ) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        projectService.deleteProject(projectId, user.getId()); // Elimina el proyecto si pertenece al usuario
        MessageResponse res = new MessageResponse("Project deleted successfully"); // Crea un mensaje de respuesta
        return new ResponseEntity<>(res, HttpStatus.OK); // Devuelve el mensaje de éxito
    }

    // Buscar proyectos por palabra clave
    @GetMapping("/search") 
    public ResponseEntity<List<Project>> searchProjects(
        @RequestParam(required = false) String keyword, // Parámetro opcional para buscar por palabra clave
        @RequestHeader("Authorization") String jwt // JWT del usuario
        ) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        List<Project> projects = projectService.searchProjects(keyword, user); // Busca proyectos por palabra clave
        return new ResponseEntity<>(projects, HttpStatus.OK); // Devuelve la lista de proyectos encontrados
    }

    // Obtener el chat de un proyecto
    @GetMapping("/{projectId}/chat") 
    public ResponseEntity<Chat> getChatByProjectId(
        @PathVariable Long projectId, // ID del proyecto
        @RequestHeader("Authorization") String jwt // JWT del usuario
        ) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        Chat chat = projectService.getChatByProjectId(projectId); // Obtiene el chat asociado al proyecto
        return new ResponseEntity<>(chat, HttpStatus.OK); // Devuelve el chat con estado 200 OK
    } 

    @PostMapping("/invite") // Ruta para invitar a un usuario a un proyecto
    public ResponseEntity<MessageResponse> inviteProject(
        @RequestBody InviteRequest req, // Cuerpo de la solicitud con datos de invitación
        @RequestHeader("Authorization") String jwt, // Encabezado con JWT
        @RequestBody Project project // Cuerpo de la solicitud con datos del nuevo proyecto
        ) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        invitationService.sendInvitation(req.getEmail(), req.getProjectId()); // Envía la invitación al correo electrónico
        MessageResponse res = new MessageResponse("Invitation sent successfully"); // Crea un mensaje de respuesta
        return new ResponseEntity<>(res, HttpStatus.OK); // Devuelve el mensaje de éxito
    }

    @GetMapping("/accept_invitation") // Ruta para aceptar una invitación a un proyecto
    public ResponseEntity<Invitation> acceptInviteProject(
        @RequestParam String token, // Token de invitación recibido como parámetro
        @RequestBody InviteRequest req, // Cuerpo de la solicitud con datos de invitación
        @RequestHeader("Authorization") String jwt, // Encabezado con JWT
        @RequestBody Project project // Cuerpo de la solicitud con datos del nuevo proyecto
        ) throws Exception {
        
        User user = userService.findUserProfileByJwt(jwt); // Obtiene el usuario a partir del JWT
        Invitation invitation = invitationService.acceptInvitation(token, user.getId()); // Acepta la invitación utilizando el token y el ID del usuario
        projectService.addUserToProject(invitation.getProjectId(), user.getId()); // Agrega al usuario al proyecto
        return new ResponseEntity<>(invitation, HttpStatus.ACCEPTED); // Devuelve el mensaje de éxito
    }
}
