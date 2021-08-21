package matej.tejkogames.interfaces.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.models.general.User;

@RestController
@CrossOrigin(origins = { TejkoGamesConstants.ORIGIN_DEFAULT, TejkoGamesConstants.ORIGIN_WWW,
        TejkoGamesConstants.ORIGIN_HEROKU })
@RequestMapping("/api/users")
public interface UserController extends ControllerInterface<User> {
    
}
