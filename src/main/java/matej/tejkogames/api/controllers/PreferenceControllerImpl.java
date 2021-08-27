package matej.tejkogames.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.PreferenceServiceImpl;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.controllers.PreferenceController;
import matej.tejkogames.models.general.Preference;
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.requests.PreferenceRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
		TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/preferences")
public class PreferenceControllerImpl implements PreferenceController {

    @Autowired
    PreferenceServiceImpl preferenceService;

    @PreAuthorize("hasAuthority('ADMIN') or @authPermissionComponent.hasPermission(@jwtUtil.getUsernameFromHeader(#headerAuth), #id, 'Preference')")
    @GetMapping("/{id}")
    public ResponseEntity<Preference> getById(@PathVariable UUID id) {
        return new ResponseEntity<>(preferenceService.getById(id), HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<Preference>> getAll() {
        return new ResponseEntity<>(preferenceService.getAll(), HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable UUID id) {
        preferenceService.deleteById(id);
        return new ResponseEntity<>(new MessageResponse("Preference", MessageType.DEFAULT, "Preference with id " + id + " has been successfully deleted"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<MessageResponse> deleteAll() {
        preferenceService.deleteAll();
        return new ResponseEntity<>(new MessageResponse("Preference", MessageType.DEFAULT, "All preferences have been successfully deleted"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN') or @authPermissionComponent.hasPermission(@jwtUtil.getUsernameFromHeader(#headerAuth), #id, 'Preference')")
    @PatchMapping("/{id}")
    public ResponseEntity<Preference> updateById(@PathVariable UUID id, @RequestBody PreferenceRequest preferenceRequest) {
        return new ResponseEntity<>(preferenceService.updateById(id, preferenceRequest), HttpStatus.OK);
    }
    
}
