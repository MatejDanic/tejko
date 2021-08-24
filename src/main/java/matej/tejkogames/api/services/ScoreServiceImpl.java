package matej.tejkogames.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.models.general.Score;
import matej.tejkogames.models.general.payload.requests.ScoreRequest;
import matej.tejkogames.api.repositories.ScoreRepository;
import matej.tejkogames.interfaces.services.ScoreService;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    ScoreRepository scoreRepository;

    public Score getById(UUID id) {
        return scoreRepository.getById(id);
    }

    public List<Score> getAll() {
        return scoreRepository.findAll();
    }

    public void deleteById(UUID id) {
        scoreRepository.deleteById(id);
    }

    public void deleteAll() {
        scoreRepository.deleteAll();
    }

    public List<Score> getAllByDateBetween(LocalDateTime start, LocalDateTime end) {
        return scoreRepository.findAllByDateBetween(start, end);
    }

    public Score updateById(UUID id, ScoreRequest scoreRequest) {
        return null;
    }

}
