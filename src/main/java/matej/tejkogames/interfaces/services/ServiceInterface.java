package matej.tejkogames.interfaces.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface ServiceInterface<T> {

    public T getById(UUID id);

    public List<T> getAll();

    public void deleteAll();

    public void deleteById(UUID id);
    
}
