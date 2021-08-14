package matej.tejkogames.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.ExceptionLog;

public interface ExceptionLogRepository extends JpaRepository<ExceptionLog, Integer> {
    
}
