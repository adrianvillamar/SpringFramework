package com.adrian.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adrian.model.Issue;
import com.adrian.model.IssueDTO;
import com.adrian.model.User;
import com.adrian.request.IssueRequest;
import com.adrian.response.MessageResponse;
import com.adrian.services.IssueService;
import com.adrian.services.UserService;

@RestController
@RequestMapping("/api/issues")  // Mapea las rutas bajo /api/issues
public class IssueController {

    @Autowired
    private IssueService issueService;  // Inyecta el servicio de problemas para interactuar con la lógica de negocio

    @Autowired
    private UserService userService;  // Inyecta el servicio de usuarios para interactuar con la lógica de negocio

    @GetMapping("/{issueId}") 
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueId) throws Exception {
        Issue issue = issueService.getIssueById(issueId);  // Llama al servicio para obtener el problema por ID
        return ResponseEntity.ok(issue);  // Devuelve el problema encontrado    
    }
    
    @GetMapping("/project/{projectId}") 
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception {
        return ResponseEntity.ok(issueService.getIssueByProjectId(projectId));  // Devuelve el problema encontrado    
    }

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(
        @RequestBody IssueRequest issue,
        @RequestHeader("Authorization") String token) 
        throws Exception {

            User tokenUser = userService.findUserProfileByJwt(token);  // Obtiene el usuario del token JWT
            User user = userService.findUserById(tokenUser.getId());  // Obtiene el usuario por ID

            Issue createdIssue = issueService.createIssue(issue, tokenUser);  // Crea el problema
            IssueDTO issueDTO = new IssueDTO();  // Crea un DTO para el problema
            issueDTO.setId(createdIssue.getId());  // Establece el ID del problema creado
            issueDTO.setTitle(createdIssue.getTitle());  // Establece el título del problema creado
            issueDTO.setDescription(createdIssue.getDescription());  // Establece la descripción del problema creado
            issueDTO.setStatus(createdIssue.getStatus());  // Establece el estado del problema creado
            issueDTO.setPriority(createdIssue.getPriority());  // Establece la prioridad del problema creado
            issueDTO.setDueDate(createdIssue.getDueDate());  // Establece la fecha de vencimiento del problema creado
            issueDTO.setProjectId(createdIssue.getProjectID());  // Establece el ID del proyecto del problema creado
            issueDTO.setTags(createdIssue.getTags());  // Establece las etiquetas del problema creado
            issueDTO.setProject(createdIssue.getProject());  // Establece el proyecto del problema creado
            issueDTO.setAssignee(createdIssue.getAssignee());  // Establece el asignado del problema creado
            return ResponseEntity.ok(issueDTO);  // Devuelve el DTO del problema creado
    }

    @DeleteMapping("/{issueId}") 
    public ResponseEntity<MessageResponse> deleteIssue(
        @PathVariable Long issueId,
        @RequestHeader("Authorization") String token) 
        throws Exception {

            User user = userService.findUserProfileByJwt(token);  // Obtiene el usuario del token JWT
            issueService.deleteIssue(issueId, user.getId());  // Llama al servicio para eliminar el problema

            MessageResponse res = new MessageResponse();  // Crea una respuesta de mensaje
            res.setMessage("Issue deleted successfully");  // Establece el mensaje de éxito
            return ResponseEntity.ok(res); 
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<Issue> addUserToIssue(
        @PathVariable Long issueId,
        @PathVariable Long userId) 
        throws Exception {

            Issue issue = issueService.addUserToIssue(issueId, userId);  // Llama al servicio para agregar un usuario al problema
            return ResponseEntity.ok(issue);  // Devuelve el problema actualizado
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<Issue> updateIssueStatus(
        @PathVariable Long issueId,
        @PathVariable String status) 
        throws Exception {

            Issue issue = issueService.updateIssue(issueId, status);  // Llama al servicio para actualizar el problema
            return ResponseEntity.ok(issue);  // Devuelve el problema actualizado
    }      
}
