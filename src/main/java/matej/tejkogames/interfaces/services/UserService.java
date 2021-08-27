package matej.tejkogames.interfaces.services;

import java.util.UUID;

import matej.tejkogames.models.general.User;

public interface UserService extends ServiceInterface<User, UUID> {

    public boolean hasPermission(UUID id, String username);

}
