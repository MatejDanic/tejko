package matej.tejkogames.interfaces.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import matej.tejkogames.models.general.payload.responses.MessageResponse;

public interface ControllerInterface<T, I> {

    public ResponseEntity<T> getById(I id);

    public ResponseEntity<List<T>> getAll();

    public ResponseEntity<MessageResponse> deleteById(String headerAuth, I id);

    public ResponseEntity<MessageResponse> deleteAll(String headerAuth);

}
