package matej.tejkogames.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.yamb.YambChallenge;

public interface YambChallengeRepository extends JpaRepository<YambChallenge, UUID> {

}
