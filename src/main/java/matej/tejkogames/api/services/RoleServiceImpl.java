package matej.tejkogames.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.api.repositories.UserRepository;
import matej.tejkogames.interfaces.services.RoleService;
import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.User;
import matej.tejkogames.models.general.payload.requests.RoleRequest;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public Role getById(Integer id) {
        return roleRepository.getById(id);
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);     
    }

    public void deleteAll() {
        roleRepository.deleteAll();     
    }

    public Role updateById(Integer id, RoleRequest roleRequest) {
        Role role = getById(id);
        if (roleRequest.getLabel() != null) {
            role.setLabel(roleRequest.getLabel());
        }
        if (roleRequest.getDescription() != null) {
            role.setDescription(roleRequest.getDescription());
        }
        return roleRepository.save(role);
    }

    public List<User> getUsersByRolesId(Integer id) {
        return userRepository.findAllByRolesId(id);
    }
    
}
