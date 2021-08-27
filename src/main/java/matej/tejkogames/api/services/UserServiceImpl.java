package matej.tejkogames.api.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.PreferenceRepository;
import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.api.repositories.ScoreRepository;
import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.api.repositories.YambRepository;
import matej.tejkogames.constants.TejkoGamesConstants;
import matej.tejkogames.interfaces.services.UserService;
import matej.tejkogames.models.general.Preference;
import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.yamb.Yamb;

/**
 * Service Class for managing {@link User} repostiory
 *
 * @author MatejDanic
 * @version 1.0
 * @since 2020-08-20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PreferenceRepository preferenceRepository;
    
    @Autowired
    YambRepository yambRepository;

    @Autowired
    ScoreRepository scoreRepository;

    public User getById(UUID id) {
        return userRepository.findById(id).get();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public boolean hasPermission(UUID id, String username) {
        return userRepository.getById(id).getUsername().equals(username);
    }

    // public Yamb getYambByTypeAndByUserId(UUID id, YambType type) {
    //     Yamb yamb;
    //     if (getById(id).getYambByType(type) != null) {
    //         yamb = getById(id).getYambByType(type);
    //     } else {
    //         yamb = YambUtil.generateYamb(YambConstants.DEFAULT_TYPE, YambConstants.NUMBER_OF_COLUMNS, YambConstants.NUMBER_OF_DICE);
    //         yamb.setUser(getById(id));
    //         yamb = yambRepository.save(yamb);
    //     }
    //     return yamb;
    // }    

    public Set<Yamb> getYambsByUserId(UUID id) {
        return getById(id).getYambs();
    }
    
    public Preference getPreferenceByUserId(UUID id) {
        Preference preference;
        if (getById(id).getPreference() != null) {
            preference = getById(id).getPreference();
        } else {
            preference = savePreferenceByUserId(id);
        }
        return preference;
    }

    public void deletePreferenceByUserId(UUID id) {
        preferenceRepository.deleteById(getById(id).getPreference().getId());
    }

    public Preference savePreferenceByUserId(UUID id) {
        return preferenceRepository.save(new Preference(TejkoGamesConstants.DEFAULT_VOLUME, TejkoGamesConstants.DEFAULT_THEME));
    }

    public Set<Role> assignRoleByUserId(UUID id, String roleLabel) {

        User user = userRepository.findById(id).get();
        Set<Role> roles = user.getRoles();

        roles.add(roleRepository.findByLabel(roleLabel)
                .orElseThrow(() -> new RuntimeException("Uloga '" + roleLabel + "' nije pronađena.")));

        user.setRoles(roles);
        userRepository.save(user);

        return user.getRoles();
    }

    public List<Score> getScoresByUserId(UUID id) {
        return scoreRepository.findAllByUserId(id);
    }

}