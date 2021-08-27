package matej.tejkogames.interfaces.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.general.payload.requests.ScoreRequest;

public interface ScoreController extends ControllerInterface<Score, UUID> {

    public ResponseEntity<Score> updateById(UUID id, ScoreRequest scoreRequest);
    
}
