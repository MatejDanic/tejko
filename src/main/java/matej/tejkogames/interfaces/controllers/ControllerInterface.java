package matej.tejkogames.interfaces.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface ControllerInterface<T> {

    public ResponseEntity<Object> getById(UUID id);

    public ResponseEntity<Object> getAll();

    public ResponseEntity<Object> deleteAll();

    public ResponseEntity<Object> deleteById(UUID id);
    
}
