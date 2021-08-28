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
import matej.tejkogames.exceptions.YambLimitReachedException;
import matej.tejkogames.interfaces.services.UserService;
import matej.tejkogames.models.general.Preference;
import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.general.payload.requests.RoleRequest;
import matej.tejkogames.models.general.payload.requests.YambRequest;
import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.yamb.Yamb;
import matej.tejkogames.models.yamb.YambType;

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

    public Yamb createYambByUserId(UUID id, YambRequest yambRequest) {
        User user = getById(id);
        int yambLimit = 0;
        if (yambRequest.getType() == YambType.CLASSIC || yambRequest.getType() == YambType.CUSTOM) {
            yambLimit = YambConstants.YAMB_LIMIT;
            
        } else if (yambRequest.getType() == YambType.CHALLENGE) {
            yambLimit = YambConstants.CHALLENGE_LIMIT;
        }
        int yambCounter = 0;
        boolean limitReached = false;
        for (Yamb userYamb : getYambsByTypeAndUserId(id, yambRequest.getType())) {
            if (userYamb.getType() == yambRequest.getType()) {
                yambCounter++;
                if (yambCounter >= yambLimit) {
                    limitReached = true;
                }
            }
        }
        if (limitReached) {
            throw new YambLimitReachedException("Yamb limit of " + yambLimit + " has been reached by user " + user.getUsername());
        } else {
            Yamb yamb = new Yamb(user, yambRequest.getType(), yambRequest.getNumberOfColumns(), yambRequest.getNumberOfDice());
            return yambRepository.save(yamb);
        }
    }

    public List<Yamb> getYambsByTypeAndUserId(UUID id, YambType type) {
        return yambRepository.findAllByTypeAndUserId(id, type);
    }

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

    public Set<Role> assignRoleByUserId(UUID id, RoleRequest roleRequest) {

        User user = userRepository.findById(id).get();
        Set<Role> roles = user.getRoles();

        roles.add(roleRepository.findByLabel(roleRequest.getLabel())
                .orElseThrow(() -> new RuntimeException("Uloga '" + roleRequest.getLabel() + "' nije pronađena.")));

        user.setRoles(roles);
        userRepository.save(user);

        return user.getRoles();
    }

    public List<Score> getScoresByUserId(UUID id) {
        return scoreRepository.findAllByUserId(id);
    }

    public boolean hasPermission(UUID id, String username) {
        return getById(id).getUsername().equals(username);
    }

}