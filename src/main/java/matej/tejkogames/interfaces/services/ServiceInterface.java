package matej.tejkogames.interfaces.services;

import java.util.List;

public interface ServiceInterface<T, I> {

    public T getById(I id);

    public List<T> getAll();

    public void deleteById(I id);

    public void deleteAll();

}
