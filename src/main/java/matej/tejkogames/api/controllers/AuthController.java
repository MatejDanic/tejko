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

import matej.tejkogames.models.payload.responses.MessageResponse;
import matej.tejkogames.models.payload.requests.LoginRequest;
import matej.tejkogames.models.payload.requests.RegisterRequest;
import matej.tejkogames.api.services.AuthService;

@RestController
@CrossOrigin(origins = {"http://tejko.games", "http://www.tejko.games", "https://tejko-games.herokuapp.com" })
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            authService.login(loginRequest);
            return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>(new MessageResponse("Prijava", exc.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return new ResponseEntity<>(new MessageResponse("Registracija", "Korisnik uspješno registriran"), HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>(new MessageResponse("Registracija", exc.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}