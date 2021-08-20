package matej.tejkogames.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.ApiErrorServiceImpl;
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = {"http://tejko.games", "http://www.tejko.games", "https://tejko-games.herokuapp.com" })
@RequestMapping("/api/errors")
public class ApiErrorController {

    
    @Autowired
    ApiErrorServiceImpl apiErrorService;

    @PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/")
	public ResponseEntity<Object> deleteAll() {
		try {
			apiErrorService.deleteAll();
			return new ResponseEntity<>(new MessageResponse("All Exception Logs have been deleted."), HttpStatus.OK);
		} catch (Exception exception) {
			apiErrorService.save(exception);
			return new ResponseEntity<>(new MessageResponse("Exception Log Purge", MessageType.ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}


    
}
