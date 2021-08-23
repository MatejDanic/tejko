package matej.tejkogames.components;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.api.repositories.YambRepository;

@Component
public class AuthPermissionComponent {

    @Autowired
    UserRepository userRepository;

    @Autowired
    YambRepository yambRepository;

    public boolean hasPermission(String username, UUID id, String object) {
        boolean hasPermission = false;
        switch (object) {
            case "User":
                hasPermission = userRepository.getById(id).getUsername().equals(username);
                break;
            case "Yamb":
                hasPermission = yambRepository.getById(id).getUser().getUsername().equals(username);
                break;
            default:

        }
        return hasPermission;
    }

}