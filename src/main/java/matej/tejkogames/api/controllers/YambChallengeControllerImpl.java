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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.api.services.YambChallengeServiceImpl;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.controllers.YambChallengeController;
import matej.tejkogames.models.yamb.YambChallenge;
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
    TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/challenges")
public class YambChallengeControllerImpl implements YambChallengeController {

    @Autowired
    YambChallengeServiceImpl yambChallengeService;

    @PreAuthorize("hasAuthority('ADMIN') or @authPermissionComponent.hasPermission(@jwtUtil.getUsernameFromHeader(#headerAuth), #id, 'YambChallenge')")
    @GetMapping("/{id}")
    public ResponseEntity<YambChallenge> getById(UUID id) {
        return new ResponseEntity<>(yambChallengeService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<YambChallenge>> getAll() {
        return new ResponseEntity<>(yambChallengeService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@RequestHeader(value = "Authorization") String headerAuth, @PathVariable UUID id) {
        yambChallengeService.deleteById(id);
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<MessageResponse> deleteAll(@RequestHeader(value = "Authorization") String headerAuth) {
        yambChallengeService.deleteAll();
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }
    
}
