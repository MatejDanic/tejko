package matej.tejkogames.interfaces.services;

import java.util.List;
import java.util.UUID;

public interface ServiceInterface<T> {

    public T getById(UUID id);

    public List<T> getAll();

    public void deleteById(UUID id);

    public void deleteAll();

}
