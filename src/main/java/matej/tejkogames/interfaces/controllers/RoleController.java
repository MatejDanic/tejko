package matej.tejkogames.interfaces.controllers;

import org.springframework.http.ResponseEntity;

import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.payload.requests.RoleRequest;

public interface RoleController extends ControllerInterface<Role, Integer> {

    public ResponseEntity<Role> updateById(Integer id, RoleRequest roleRequest);
    
}
