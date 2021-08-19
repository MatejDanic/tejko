package matej.tejkogames.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.yamb.Score;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
    
}
