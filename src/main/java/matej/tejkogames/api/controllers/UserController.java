package matej.tejkogames.api.controllers;

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

import matej.tejkogames.api.services.ExceptionLogService;
import matej.tejkogames.api.services.UserService;
import matej.tejkogames.exceptions.InvalidOwnershipException;
import matej.tejkogames.models.general.ExceptionLog;
import matej.tejkogames.models.general.payload.requests.PreferenceRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;
import matej.tejkogames.utils.JwtUtil;

@RestController
@CrossOrigin(origins = {"http://tejko.games", "http://www.tejko.games", "https://tejko-games.herokuapp.com" })
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	ExceptionLogService exceptionLogService;
	
	@Autowired
	JwtUtil jwtUtil;

	@GetMapping("")
	public ResponseEntity<Object> getUsers() {
		try {
			return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
		} catch (Exception exception) {
			exceptionLogService.save(new ExceptionLog(exception.getMessage()));
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		try {
			return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
		} catch (Exception exception) {
			exceptionLogService.save(new ExceptionLog(exception.getMessage()));
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	// @GetMapping("/{id}/form")
	// public ResponseEntity<Object> getUserForm(@PathVariable int id) {
	// 	try {
	// 		return new ResponseEntity<>(userService.getUserById(id).getForm(), HttpStatus.OK);
	// 	} catch (Exception exception) {
	// 		return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
	// 	}
	// }

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteUserById(@PathVariable int id) {
		try {
			userService.deleteUserById(id);
			return new ResponseEntity<>(new MessageResponse("Korisnik uspješno izbrisan."), HttpStatus.OK);
		} catch (Exception exception) {
			exceptionLogService.save(new ExceptionLog(exception.getMessage()));
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/{id}/assign-role")
	public ResponseEntity<Object> deleteUserById(@PathVariable int id, @RequestBody String roleLabel) {
		try {
			return new ResponseEntity<>(userService.assignRole(id, roleLabel), HttpStatus.OK);
		} catch (Exception exception) {
			exceptionLogService.save(new ExceptionLog(exception.getMessage()));
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}/preferences")
	public ResponseEntity<Object> getUserPreferences(@PathVariable int id) {
		try {
			return new ResponseEntity<>(userService.getUserPreference(id), HttpStatus.OK);
		} catch (Exception exception) {
			exceptionLogService.save(new ExceptionLog(exception.getMessage()));
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}/preferences")
	public ResponseEntity<Object> updatePreference(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") int id, @RequestBody PreferenceRequest preferenceRequest) {
		try {
			if (!userService.checkOwnership(jwtUtil.getUsernameFromHeader(headerAuth), id)) {
				throw new InvalidOwnershipException("Korisnik nema ovlasti nad korisničkim računom s id-em " + id);
			}
			return new ResponseEntity<>(userService.updateUserPreference(id, preferenceRequest), HttpStatus.OK);
		} catch (InvalidOwnershipException exception) {
			exceptionLogService.save(new ExceptionLog(exception.getMessage()));
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
