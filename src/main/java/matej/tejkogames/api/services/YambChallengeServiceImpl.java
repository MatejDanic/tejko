package matej.tejkogames.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.YambChallengeRepository;
import matej.tejkogames.interfaces.services.YambChallengeService;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.yamb.YambChallenge;

@Service
public class YambChallengeServiceImpl implements YambChallengeService {

    @Autowired
    YambChallengeRepository yambChallengeRepository;

    public YambChallenge getById(UUID id) {
        return yambChallengeRepository.findById(id).get();
    }

    public List<YambChallenge> getAll() {
        return yambChallengeRepository.findAll();
    }

    public void deleteById(UUID id) {
        yambChallengeRepository.deleteById(id);
    }

    public void deleteAll() {
        yambChallengeRepository.deleteAll();
    }

    public boolean hasPermission(UUID id, String username) {
        for (User user : getById(id).getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
