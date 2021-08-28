
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

import matej.tejkogames.api.services.YambMatchServiceImpl;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.controllers.YambMatchController;
import matej.tejkogames.models.yamb.YambMatch;
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
        TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/matches")
public class YambMatchControllerImpl implements YambMatchController {

    @Autowired
    YambMatchServiceImpl yambMatchService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<YambMatch> getById(UUID id) {
        return new ResponseEntity<>(yambMatchService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<YambMatch>> getAll() {
        return new ResponseEntity<>(yambMatchService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@RequestHeader(value = "Authorization") String headerAuth, @PathVariable UUID id) {
        yambMatchService.deleteById(id);
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<MessageResponse> deleteAll(@RequestHeader(value = "Authorization") String headerAuth) {
        yambMatchService.deleteAll();
        return new ResponseEntity<>(new MessageResponse("", MessageType.DEFAULT, ""), HttpStatus.OK);
    }

}
