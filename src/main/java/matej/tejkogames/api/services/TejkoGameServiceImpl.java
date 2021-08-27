package matej.tejkogames.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.ScoreRepository;
import matej.tejkogames.api.repositories.TejkoGameRepository;
import matej.tejkogames.interfaces.services.TejkoGameService;
import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.general.TejkoGame;
import matej.tejkogames.models.general.payload.requests.TejkoGameRequest;

@Service
public class TejkoGameServiceImpl implements TejkoGameService {

    @Autowired
    TejkoGameRepository tejkoGameRepository;
    
    @Autowired
    ScoreRepository scoreRepository;

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

    public TejkoGame updateById(Integer id, TejkoGameRequest tejkoGameRequest) {
        TejkoGame tejkoGame = getById(id);
        if (tejkoGameRequest.getName() != null) {
            tejkoGame.setName(tejkoGameRequest.getName());
        }
        if (tejkoGameRequest.getDescription() != null) {
            tejkoGame.setDescription(tejkoGameRequest.getDescription());
        }
        return tejkoGameRepository.save(tejkoGame);
    }

    public List<Score> getScoresByGameId(Integer id) {
        return scoreRepository.findAllByGameId(id);
    }
    
}
