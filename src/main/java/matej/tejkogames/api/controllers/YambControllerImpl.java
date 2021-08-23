package matej.tejkogames.api.controllers;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import matej.tejkogames.interfaces.controllers.YambController;
import matej.tejkogames.models.general.payload.requests.YambRequest;
import matej.tejkogames.models.general.payload.responses.MessageResponse;
import matej.tejkogames.models.yamb.BoxType;
import matej.tejkogames.models.yamb.ColumnType;
import matej.tejkogames.models.yamb.Dice;
import matej.tejkogames.models.yamb.Yamb;
import matej.tejkogames.utils.JwtUtil;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
		TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/yambs")
public class YambControllerImpl implements YambController {

	@Autowired
	YambServiceImpl yambService;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	JwtUtil jwtUtil;

	@PreAuthorize("hasAuthority('ADMIN') or @authPermissionComponent.hasPermission(@jwtUtil.getUsernameFromHeader(#headerAuth), #id, 'Yamb')")
	@GetMapping("/{id}")
	public ResponseEntity<Yamb> getById(@PathVariable(value = "id") UUID id) {
		return new ResponseEntity<>(yambService.getById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("")
	public ResponseEntity<List<Yamb>> getAll() {
		return new ResponseEntity<>(yambService.getAll(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN') or @authPermissionComponent.hasPermission(@jwtUtil.getUsernameFromHeader(#headerAuth), #id, 'Yamb')")
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteById(@PathVariable(value = "id") UUID id) {
		yambService.deleteById(id);
		return new ResponseEntity<>(new MessageResponse("Yamb s id-em " + id + " uspješno izbrisan."), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("")
	public ResponseEntity<MessageResponse> deleteAll() {
		yambService.deleteAll();
		return new ResponseEntity<>(new MessageResponse("Svi Yambovi uspješno izbrisani."), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ADMIN') or @authPermissionComponent.hasPermission(@jwtUtil.getUsernameFromHeader(#headerAuth), #id, 'Yamb')")
	@PutMapping("/{id}/roll")
	public ResponseEntity<Set<Dice>> rollDiceById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id) throws IllegalMoveException {
		return new ResponseEntity<>(yambService.rollDiceById(id), HttpStatus.OK);

	}

	@PreAuthorize("hasAuthority('ADMIN') or @authPermissionComponent.hasPermission(@jwtUtil.getUsernameFromHeader(#headerAuth), #id, 'Yamb')")
	@PutMapping("/{id}/announce")
	public ResponseEntity<BoxType> announceById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id, @RequestBody BoxType boxType) throws IllegalMoveException {
		return new ResponseEntity<>(yambService.announceById(id, boxType), HttpStatus.OK);

	}

	@PutMapping("/{id}/columns/{columnTypeId}/boxes/{boxTypeId}/fill")
	public ResponseEntity<Yamb> fillById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id, @PathVariable(value = "columnType") ColumnType columnType,
			@PathVariable(value = "boxType") BoxType boxType) throws IllegalMoveException {
		return new ResponseEntity<>(yambService.fillById(id, columnType, boxType), HttpStatus.OK);

	}

	@PutMapping("/{id}/restart")
	public ResponseEntity<Yamb> restartById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id) {
		return new ResponseEntity<>(yambService.restartById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}/recreate")
	public ResponseEntity<Yamb> recreateById(@RequestHeader(value = "Authorization") String headerAuth,
			@PathVariable(value = "id") UUID id, @RequestBody YambRequest yambRequest) {
		return new ResponseEntity<>(yambService.recreateById(id, yambRequest), HttpStatus.OK);
	}

}
