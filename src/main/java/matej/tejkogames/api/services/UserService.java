package matej.tejkogames.api.services;

import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.PreferenceRepository;
import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.models.User;
import matej.tejkogames.models.Role;
import matej.tejkogames.models.Preference;
import matej.tejkogames.models.payload.requests.PreferenceRequest;

/**
 * Service Class for managing {@link User} repostiory
 *
 * @author MatejDanic
 * @version 1.0
 * @since 2020-08-20
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Autowired
    PreferenceRepository prefRepo;

    public Preference getUserPreference(int userId) {
        return userRepo.findById(userId).get().getPreference();
    }

    public Preference updateUserPreference(int userId, PreferenceRequest prefRequest) {
        User user = userRepo.findById(userId).get();
        Preference preference = user.getPreference();
        if (preference != null) {
            if (prefRequest.getVolume() != null) {
                preference.setVolume(prefRequest.getVolume());
            }
        } else {
            preference = new Preference();
            preference.setUser(user);
            if (prefRequest.getVolume() != null) {
                if (prefRequest.getVolume() < 0 || prefRequest.getVolume() > 3) {
                    throw new IllegalArgumentException("Glasnoća zvuka mora biti unutar granica! [0-3]");
                }
                preference.setVolume(prefRequest.getVolume());
            }
        }
        user.setPreference(preference);
        userRepo.save(user);
        return user.getPreference();
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public void deleteUserById(int id) {
        userRepo.deleteById(id);
    }

    public User getUserById(int id) {
        return userRepo.findById(id).get();
    }

    // public List<GameScore> getUserScores(int id) {
    //     return userRepo.findById(id).get().getScores();
    // }

    // public boolean checkFormOwnership(String username, int formId) {
    //     User user = userRepo.findByUsername(username)
    //             .orElseThrow(() -> new UsernameNotFoundException("Korisnik s imenom " + username + " nije pronađen."));
    //     return user.getForm().getId() == formId;
    // }

    public boolean checkOwnership(String username, int userId) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik s imenom " + username + " nije pronađen."));
        return user.getId() == userId;

    }

    public Set<Role> assignRole(int userId, String roleLabel) {

        User user = userRepo.findById(userId).get();
        Set<Role> roles = user.getRoles();
        
        roles.add(roleRepo.findByLabel(roleLabel).orElseThrow(() -> new RuntimeException("Uloga '" + roleLabel + "' nije pronađena.")));
        
        user.setRoles(roles);
        userRepo.save(user);
        
        return user.getRoles();
        
    }
}