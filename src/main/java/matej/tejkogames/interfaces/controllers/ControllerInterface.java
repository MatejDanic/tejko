package matej.tejkogames.interfaces.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import matej.tejkogames.models.general.payload.responses.MessageResponse;

public interface ControllerInterface<T> {

    public ResponseEntity<T> getById(UUID id);

    public ResponseEntity<List<T>> getAll();

    public ResponseEntity<MessageResponse> deleteById(UUID id);
    
    public ResponseEntity<MessageResponse> deleteAll();
    
}
