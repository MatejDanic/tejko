package matej.tejkogames.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.UserService;
import matej.tejkogames.api.services.YambService;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.exceptions.IllegalMoveException;
import matej.tejkogames.exceptions.InvalidOwnershipException;
import matej.tejkogames.models.general.payload.requests.YambRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;
import matej.tejkogames.models.yamb.BoxType;
import matej.tejkogames.models.yamb.ColumnType;
import matej.tejkogames.utils.JwtUtil;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW, TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/yambs")
public class YambController {

    @Autowired
	YambService yambService;

	@Autowired
	UserService userService;

	@Autowired
	JwtUtil jwtUtil;

	@PutMapping("")
	public ResponseEntity<Object> initializeYamb(@RequestHeader(value = "Authorization") String headerAuth, @RequestBody YambRequest yambRequest) {
		try {
			return new ResponseEntity<>(yambService.initializeYamb(jwtUtil.getUsernameFromHeader(headerAuth), yambRequest.getType(), yambRequest.getNumberOfColumns(), yambRequest.getNumberOfDice()),
					HttpStatus.OK);
		} catch (UsernameNotFoundException exc) {
			return new ResponseEntity<>(new MessageResponse(exc.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}/roll")
	public ResponseEntity<Object> rollDice(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") int id) {
		try {
			if (!userService.checkYambOwnership(jwtUtil.getUsernameFromHeader(headerAuth), id)) {
				throw new InvalidOwnershipException("Yamb s id-em " + id + " ne pripada korisniku.");
			}
			return new ResponseEntity<>(yambService.rollDice(id), HttpStatus.OK);
		} catch (InvalidOwnershipException | IllegalMoveException exc) {
			return new ResponseEntity<>(new MessageResponse(exc.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}/announce")
	public ResponseEntity<Object> announce(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") int id, @RequestBody BoxType boxType) {
		try {
			if (!userService.checkYambOwnership(jwtUtil.getUsernameFromHeader(headerAuth), id)) {
				throw new InvalidOwnershipException("Yamba s id-em " + id + " ne pripada korisniku.");
			}
			return new ResponseEntity<>(yambService.announce(id, boxType), HttpStatus.OK);
		} catch (IllegalMoveException | InvalidOwnershipException exc) {
			return new ResponseEntity<>(new MessageResponse(exc.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}/columns/{columnTypeId}/boxes/{boxTypeId}/fill")
	public ResponseEntity<Object> fillBox(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") int id, @PathVariable(value = "columnType") ColumnType columnType,
			@PathVariable(value = "boxType") BoxType boxType) {
		try {
			if (!userService.checkYambOwnership(jwtUtil.getUsernameFromHeader(headerAuth), id)) {
				throw new InvalidOwnershipException("Yamba s id-em " + id + " ne pripada korisniku.");
			}
			return new ResponseEntity<>(
					yambService.fill(id, columnType, boxType),
					HttpStatus.OK);
		} catch (IllegalMoveException | InvalidOwnershipException exc) {
			return new ResponseEntity<>(new MessageResponse(exc.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}/restart")
	public ResponseEntity<Object> restartYambById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") int id) {
		try {
			if (!userService.checkYambOwnership(jwtUtil.getUsernameFromHeader(headerAuth), id)) {
				throw new InvalidOwnershipException("Yamba s id-em " + id + " ne pripada korisniku.");
			}
			yambService.restartYambById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (InvalidOwnershipException exc) {
			return new ResponseEntity<>(new MessageResponse(exc.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("")
	public ResponseEntity<Object> getYambList() {
		try {
			return new ResponseEntity<>(yambService.getYambList(), HttpStatus.OK);
		} catch (Exception exc) {
			return new ResponseEntity<>(new MessageResponse(exc.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getYambById(@PathVariable(value = "id") int id) {
		try {
			return new ResponseEntity<>(yambService.getYambById(id), HttpStatus.OK);
		} catch (Exception exc) {
			return new ResponseEntity<>(new MessageResponse(exc.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

}
