package matej.tejkogames.api.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.PreferenceRepository;
import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.models.general.Preference;
import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.general.payload.requests.PreferenceRequest;

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
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PreferenceRepository prefRepository;

    public Preference getUserPreference(UUID userId) {
        return userRepository.findById(userId).get().getPreference();
    }

    public Preference updateUserPreference(UUID userId, PreferenceRequest prefRequest) {
        User user = userRepository.findById(userId).get();
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
        userRepository.save(user);
        return user.getPreference();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).get();
    }

    // public List<GameScore> getUserScores(int id) {
    // return userRepo.findById(id).get().getScores();
    // }

    public boolean checkYambOwnership(String username, UUID yambId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik s imenom " + username + " nije pronađen."));
        return user.getYamb().getId() == yambId;
    }

    public boolean checkOwnership(String username, UUID userId) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Korisnik s imenom " + username + " nije pronađen."));
        return user.getId() == userId;

    }

    public Set<Role> assignRole(UUID userId, String roleLabel) {

        User user = userRepository.findById(userId).get();
        Set<Role> roles = user.getRoles();

        roles.add(roleRepository.findByLabel(roleLabel)
                .orElseThrow(() -> new RuntimeException("Uloga '" + roleLabel + "' nije pronađena.")));

        user.setRoles(roles);
        userRepository.save(user);

        return user.getRoles();

    }
}