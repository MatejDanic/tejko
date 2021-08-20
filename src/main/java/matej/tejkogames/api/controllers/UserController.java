package matej.tejkogames.api.controllers;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.UserServiceImpl;
import matej.tejkogames.exceptions.InvalidOwnershipException;
import matej.tejkogames.models.general.payload.requests.PreferenceRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;
import matej.tejkogames.utils.JwtUtil;

@RestController
@CrossOrigin(origins = { "http://tejko.games", "http://www.tejko.games", "https://tejko-games.herokuapp.com" })
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("")
	public ResponseEntity<Object> getUsers() {
		return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable UUID id) {
		return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable UUID id) {
		userService.deleteById(id);
		return new ResponseEntity<>(new MessageResponse("Korisnik uspješno izbrisan."), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/{id}/assign-role")
	public ResponseEntity<Object> assignRoleById(@PathVariable UUID id, @RequestBody String roleLabel) {
		return new ResponseEntity<>(userService.assignRoleById(id, roleLabel), HttpStatus.OK);
	}

	@GetMapping("/{id}/preferences")
	public ResponseEntity<Object> getUserPreferenceById(@PathVariable UUID id) {
		return new ResponseEntity<>(userService.getUserPreferenceById(id), HttpStatus.OK);
	}

	@PatchMapping("/{id}/preferences")
	public ResponseEntity<Object> updatePreferenceById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id, @RequestBody PreferenceRequest preferenceRequest)
			throws InvalidOwnershipException {
		return new ResponseEntity<>(
				userService.updateUserPreferenceById(jwtUtil.getUsernameFromHeader(headerAuth), id, preferenceRequest),
				HttpStatus.OK);
	}
}
