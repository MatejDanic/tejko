package matej.tejkogames.components;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matej.tejkogames.api.services.PreferenceServiceImpl;
import matej.tejkogames.api.services.UserServiceImpl;
import matej.tejkogames.api.services.YambChallengeServiceImpl;
import matej.tejkogames.api.services.YambMatchServiceImpl;
import matej.tejkogames.api.services.YambServiceImpl;

@Component
public class AuthPermissionComponent {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    YambServiceImpl yambService;

    @Autowired
    PreferenceServiceImpl preferenceService;

    @Autowired
    YambChallengeServiceImpl yambChallengeService;

    @Autowired
    YambMatchServiceImpl yambMatchService;

    public boolean hasPermission(String username, UUID id, String object) {
        boolean hasPermission = false;
        switch (object) {
            case "User":
                hasPermission = userService.hasPermission(id, username);
                break;
            case "Yamb":
                hasPermission = yambService.hasPermission(id, username);
                break;
            case "Preference":
                hasPermission = preferenceService.hasPermission(id, username);
                break;
            case "YambChallenge":
                hasPermission = yambChallengeService.hasPermission(id, username);
                break;
            case "YambMatch":
                hasPermission = yambMatchService.hasPermission(id, username);
                break;
            default:
        }
        return hasPermission;
    }

}