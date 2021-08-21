package matej.tejkogames.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.ApiErrorRepository;
import matej.tejkogames.interfaces.services.ApiErrorService;
import matej.tejkogames.models.general.ApiError;
import matej.tejkogames.models.general.User;

@Service
public class ApiErrorServiceImpl implements ApiErrorService {
    
    @Autowired
    ApiErrorRepository apiErrorRepository;
    
    public ApiError save(ApiError apiError) {
        return apiErrorRepository.save(apiError);
    }

    public ApiError save(Throwable exception) {
        return apiErrorRepository.save(new ApiError(exception));
    }

    public ApiError save(User user, Throwable exception) {
        return apiErrorRepository.save(new ApiError(user, exception));
    }

    public ApiError getById(UUID id) {
        return apiErrorRepository.getById(id);
    }

    public List<ApiError> getAll() {
        return apiErrorRepository.findAll();
    }

    public void deleteById(UUID id) {
        apiErrorRepository.deleteById(id);
    }
    
    public void deleteAll() {
        apiErrorRepository.deleteAll();
    }

}
