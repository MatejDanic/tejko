package matej.tejkogames.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.interfaces.services.RoleService;
import matej.tejkogames.models.general.Role;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

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
    
}
