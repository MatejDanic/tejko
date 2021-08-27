package matej.tejkogames.interfaces.services;

import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.payload.requests.RoleRequest;

public interface RoleService extends ServiceInterface<Role, Integer> {

    public Role updateById(Integer id, RoleRequest roleRequest);
    
}
