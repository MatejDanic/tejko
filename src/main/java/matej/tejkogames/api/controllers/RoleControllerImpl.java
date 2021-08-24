package matej.tejkogames.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.controllers.RoleController;
import matej.tejkogames.interfaces.services.RoleService;
import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
		TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/users")
public class RoleControllerImpl implements RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(Integer id) {
        return new ResponseEntity<>(roleService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getAll() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(Integer id) {
        roleService.deleteById(id);
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/")
    public ResponseEntity<MessageResponse> deleteAll() {
        roleService.deleteAll();
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }

    
    
}
