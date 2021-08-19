package matej.tejkogames.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.general.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, UUID> {

}
