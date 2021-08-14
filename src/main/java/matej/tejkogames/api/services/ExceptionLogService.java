package matej.tejkogames.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.ExceptionLogRepository;
import matej.tejkogames.models.general.ExceptionLog;

@Service
public class ExceptionLogService {
    
    @Autowired
    ExceptionLogRepository exceptionLogRepository;
    
    public ExceptionLog save(ExceptionLog exceptionLog) {
        return exceptionLogRepository.save(exceptionLog);
    }

    public void deleteAllExceptionLogs() {
        exceptionLogRepository.deleteAll();
    }

}
