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
import matej.tejkogames.constants.YambConstants;
import matej.tejkogames.interfaces.services.UserService;
import matej.tejkogames.models.general.Preference;
import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.general.payload.requests.PreferenceRequest;
import matej.tejkogames.models.yamb.Yamb;
import matej.tejkogames.utils.YambUtil;

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

    public Yamb getYambByUserId(UUID id) {
        Yamb yamb;
        if (getById(id).getYamb() != null) {
            yamb = getById(id).getYamb();
        } else {
            yamb = YambUtil.generateYamb(YambConstants.DEFAULT_TYPE, YambConstants.NUMBER_OF_COLUMNS, YambConstants.NUMBER_OF_DICE);
            yamb.setUser(getById(id));
            yamb = yambRepository.save(yamb);
        }
        return yamb;
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

    public Preference savePreferenceByUserId(UUID id) {
        return preferenceRepository.save(new Preference(TejkoGamesConstants.DEFAULT_VOLUME, TejkoGamesConstants.DEFAULT_THEME));
    }

    public Preference savePreferenceByUserId(UUID id, PreferenceRequest prefRequest) {
        User user = getById(id);
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
        return preference;
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