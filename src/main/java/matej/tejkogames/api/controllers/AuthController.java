package matej.tejkogames.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.requests.LoginRequest;
import matej.tejkogames.models.general.payload.requests.RegisterRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;
import matej.tejkogames.api.services.AuthService;
import matej.tejkogames.api.services.ExceptionLogService;

@RestController
@CrossOrigin(origins = {"http://tejko.games", "http://www.tejko.games", "https://tejko-games.herokuapp.com" })
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    ExceptionLogService exceptionLogService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
        } catch (Exception exception) {
            // exceptionLogService.save(exception);
            return new ResponseEntity<>(new MessageResponse("Login", MessageType.ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return new ResponseEntity<>(new MessageResponse("Register", "Korisnik uspješno registriran"), HttpStatus.OK);
        } catch (Exception exception) {
            // exceptionLogService.save(exception);
            return new ResponseEntity<>(new MessageResponse("Register", MessageType.ERROR, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}