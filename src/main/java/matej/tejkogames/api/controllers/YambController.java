package matej.tejkogames.api.controllers;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.UserServiceImpl;
import matej.tejkogames.api.services.YambServiceImpl;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.exceptions.IllegalMoveException;
import matej.tejkogames.exceptions.InvalidOwnershipException;
import matej.tejkogames.models.general.payload.requests.YambRequest;
import matej.tejkogames.models.yamb.BoxType;
import matej.tejkogames.models.yamb.ColumnType;
import matej.tejkogames.models.yamb.Dice;
import matej.tejkogames.models.yamb.Yamb;
import matej.tejkogames.utils.JwtUtil;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
		TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/yambs")
public class YambController {

	@Autowired
	YambServiceImpl yambService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	JwtUtil jwtUtil;

	@PutMapping("")
	public ResponseEntity<Yamb> getYamb(@RequestHeader(value = "Authorization") String headerAuth,
			@RequestBody YambRequest yambRequest) {
		return new ResponseEntity<>(yambService.getYamb(jwtUtil.getUsernameFromHeader(headerAuth),
				yambRequest.getType(), yambRequest.getNumberOfColumns(), yambRequest.getNumberOfDice()), HttpStatus.OK);

	}

	@PutMapping("/{id}/roll")
	public ResponseEntity<Set<Dice>> rollDice(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id) throws IllegalMoveException, InvalidOwnershipException {
		return new ResponseEntity<>(yambService.rollDice(jwtUtil.getUsernameFromHeader(headerAuth), id), HttpStatus.OK);

	}

	@PutMapping("/{id}/announce")
	public ResponseEntity<BoxType> announce(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id, @RequestBody BoxType boxType)
			throws IllegalMoveException, InvalidOwnershipException {
		return new ResponseEntity<>(yambService.announce(jwtUtil.getUsernameFromHeader(headerAuth), id, boxType),
				HttpStatus.OK);

	}

	@PutMapping("/{id}/columns/{columnTypeId}/boxes/{boxTypeId}/fill")
	public ResponseEntity<Yamb> fillBox(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id, @PathVariable(value = "columnType") ColumnType columnType,
			@PathVariable(value = "boxType") BoxType boxType) throws IllegalMoveException, InvalidOwnershipException {
		return new ResponseEntity<>(
				yambService.fill(jwtUtil.getUsernameFromHeader(headerAuth), id, columnType, boxType), HttpStatus.OK);

	}

	@PutMapping("/{id}/restart")
	public ResponseEntity<Yamb> restartYambById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id) throws InvalidOwnershipException {
		return new ResponseEntity<>(yambService.restartYambById(jwtUtil.getUsernameFromHeader(headerAuth), id),
				HttpStatus.OK);
	}

	@PutMapping("/{id}/recreate")
	public ResponseEntity<Yamb> recreateYambById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id, @RequestBody YambRequest yambRequest)
			throws InvalidOwnershipException {
		return new ResponseEntity<>(yambService.recreateYambById(jwtUtil.getUsernameFromHeader(headerAuth), id,
				yambRequest.getType(), yambRequest.getNumberOfColumns(), yambRequest.getNumberOfDice()), HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<Yamb>> getAll() {
		return new ResponseEntity<>(yambService.getAll(), HttpStatus.OK);
	}

	@DeleteMapping("")
	public ResponseEntity<String> deleteAll() {
		yambService.deleteAll();
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {
		yambService.deleteById(id);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Yamb> getById(@PathVariable(value = "id") UUID id) {
		return new ResponseEntity<>(yambService.getById(id), HttpStatus.OK);
	}

}
