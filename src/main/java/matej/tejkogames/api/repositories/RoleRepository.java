package matej.tejkogames.api.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.general.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {

	Optional<Role> findByLabel(String label);

}
