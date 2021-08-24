package matej.tejkogames.api.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.ScoreServiceImpl;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.controllers.ScoreController;
import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.general.payload.requests.DateIntervalRequest;
import matej.tejkogames.models.general.payload.requests.ScoreRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
		TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/scores")
public class ScoreControllerImpl implements ScoreController {

	@Autowired
	ScoreServiceImpl scoreService;

	@GetMapping("/{id}")
	public ResponseEntity<Score> getById(@PathVariable UUID id) {
		return new ResponseEntity<>(scoreService.getById(id), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<Score>> getAll() {
		return new ResponseEntity<>(scoreService.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteById(@PathVariable UUID id) {
		scoreService.deleteById(id);
		return new ResponseEntity<>(new MessageResponse("Score deleted successfully."), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("")
	public ResponseEntity<MessageResponse> deleteAll() {
		scoreService.deleteAll();
		return new ResponseEntity<>(new MessageResponse("All scores have been deleted."), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Score> updateById(@PathVariable UUID id, @RequestBody ScoreRequest scoreRequest) {
        return new ResponseEntity<>(scoreService.updateById(id, scoreRequest), HttpStatus.OK);
    }

	@GetMapping("/between")
	public ResponseEntity<List<Score>> getAllByDateBetween(@RequestBody DateIntervalRequest dateIntervalRequest) {
		return new ResponseEntity<>(
				scoreService.getAllByDateBetween(dateIntervalRequest.getStart(), dateIntervalRequest.getEnd()),
				HttpStatus.OK);
	}

}
