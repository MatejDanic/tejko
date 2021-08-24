package matej.tejkogames.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.TejkoGameRepository;
import matej.tejkogames.interfaces.services.TejkoGameService;
import matej.tejkogames.models.general.TejkoGame;

@Service
public class TejkoGameServiceImpl implements TejkoGameService {

    @Autowired
    TejkoGameRepository tejkoGameRepository;

    public TejkoGame getById(Integer id) {
        return tejkoGameRepository.getById(id);
    }

    public List<TejkoGame> getAll() {
        return tejkoGameRepository.findAll();
    }

    public void deleteById(Integer id) {
        tejkoGameRepository.deleteById(id);
    }

    public void deleteAll() {
        tejkoGameRepository.deleteAll();
    }
    
}
