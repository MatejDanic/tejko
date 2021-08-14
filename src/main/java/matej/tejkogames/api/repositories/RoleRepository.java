package matej.tejkogames.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.general.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByLabel(String label);
	
}
