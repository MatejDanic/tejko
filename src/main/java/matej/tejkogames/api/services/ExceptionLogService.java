package matej.tejkogames.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.ExceptionLogRepository;
import matej.tejkogames.models.general.ExceptionLog;
import matej.tejkogames.models.general.User;
import matej.tejkogames.utils.ExceptionLogUtil;

@Service
public class ExceptionLogService {
    
    @Autowired
    ExceptionLogRepository exceptionLogRepository;
    
    public ExceptionLog save(Throwable exception) {
        String exceptionLogMessage = ExceptionLogUtil.constructExceptionLogMessage(exception);
        return exceptionLogRepository.save(new ExceptionLog(exceptionLogMessage));
    }

    public ExceptionLog save(User user, Throwable exception) {
        String exceptionLogMessage = ExceptionLogUtil.constructExceptionLogMessage(exception);
        return exceptionLogRepository.save(new ExceptionLog(user, exceptionLogMessage));
    }

    public void deleteAllExceptionLogs() {
        exceptionLogRepository.deleteAll();
    }

}
