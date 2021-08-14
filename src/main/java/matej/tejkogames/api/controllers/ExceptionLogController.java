package matej.tejkogames.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.ExceptionLogService;
import matej.tejkogames.models.general.ExceptionLog;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = {"http://tejko.games", "http://www.tejko.games", "https://tejko-games.herokuapp.com" })
@RequestMapping("/api/exceptions")
public class ExceptionLogController {

    
    @Autowired
    ExceptionLogService exceptionLogService;

    @PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/")
	public ResponseEntity<Object> deleteAllExceptionLogs() {
		try {
			exceptionLogService.deleteAllExceptionLogs();
			return new ResponseEntity<>(new MessageResponse("All Exception Logs have been deleted."), HttpStatus.OK);
		} catch (Exception exception) {
			exceptionLogService.save(new ExceptionLog(exception.getMessage()));
			return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}


    
}
