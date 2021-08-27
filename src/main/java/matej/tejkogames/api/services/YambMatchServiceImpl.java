package matej.tejkogames.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.YambMatchRepository;
import matej.tejkogames.interfaces.services.YambMatchService;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.yamb.YambMatch;

@Service
public class YambMatchServiceImpl implements YambMatchService {

    @Autowired
    YambMatchRepository yambMatchRepository;

    public YambMatch getById(UUID id) {
        return yambMatchRepository.findById(id).get();
    }

    public List<YambMatch> getAll() {
        return yambMatchRepository.findAll();
    }

    public void deleteById(UUID id) {
        yambMatchRepository.deleteById(id);
    }

    public void deleteAll() {
        yambMatchRepository.deleteAll();
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
