package matej.tejkogames.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import matej.tejkogames.api.services.TejkoGameServiceImpl;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.controllers.TejkoGamesController;
import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.general.TejkoGame;
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.requests.TejkoGameRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
        TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/games")
public class TejkoGamesControllerImpl implements TejkoGamesController {

    @Autowired
    TejkoGameServiceImpl tejkoGameService;

    @GetMapping("/{id}")
    public ResponseEntity<TejkoGame> getById(Integer id) {
        return new ResponseEntity<>(tejkoGameService.getById(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<TejkoGame>> getAll() {
        return new ResponseEntity<>(tejkoGameService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@PathVariable Integer id) {
        tejkoGameService.deleteById(id);
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<MessageResponse> deleteAll() {
        tejkoGameService.deleteAll();
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<TejkoGame> updateById(@PathVariable Integer id,
            @RequestBody TejkoGameRequest tejkoGameRequest) {
        return new ResponseEntity<>(tejkoGameService.updateById(id, tejkoGameRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}/scores")
    public ResponseEntity<List<Score>> getScoresByGameId(Integer id) {
        return new ResponseEntity<>(tejkoGameService.getScoresByGameId(id), HttpStatus.OK);
    }

}
