package matej.tejkogames.interfaces.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.general.TejkoGame;
import matej.tejkogames.models.general.payload.requests.TejkoGameRequest;

public interface TejkoGamesController extends ControllerInterface<TejkoGame, Integer> {

    public ResponseEntity<TejkoGame> updateById(Integer id, TejkoGameRequest tejkoGameRequest);

    public ResponseEntity<List<Score>> getScoresByGameId(Integer id);
    
}
