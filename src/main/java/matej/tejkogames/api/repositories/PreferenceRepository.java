package matej.tejkogames.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.Preference;

public interface PreferenceRepository extends JpaRepository<Preference, Integer> {

}
