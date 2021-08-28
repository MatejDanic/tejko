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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.ApiErrorServiceImpl;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.controllers.ApiErrorController;
import matej.tejkogames.models.general.ApiError;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
		TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/errors")
public class ApiErrorControllerImpl implements ApiErrorController {

	@Autowired
	ApiErrorServiceImpl apiErrorService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiError> getById(@PathVariable UUID id) {
		return new ResponseEntity<>(apiErrorService.getById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("")
	public ResponseEntity<List<ApiError>> getAll() {
		return new ResponseEntity<>(apiErrorService.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteById(@RequestHeader(value = "Authorization") String headerAuth, @PathVariable UUID id) {
		apiErrorService.deleteById(id);
		return new ResponseEntity<>(new MessageResponse("ApiError uspješno izbrisan."), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("")
	public ResponseEntity<MessageResponse> deleteAll(@RequestHeader(value = "Authorization") String headerAuth) {
		apiErrorService.deleteAll();
		return new ResponseEntity<>(new MessageResponse("All Exception Logs have been deleted."), HttpStatus.OK);
	}

}
