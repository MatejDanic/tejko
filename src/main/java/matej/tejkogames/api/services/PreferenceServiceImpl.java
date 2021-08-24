package matej.tejkogames.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import matej.tejkogames.api.repositories.PreferenceRepository;
import matej.tejkogames.interfaces.services.PreferenceService;
import matej.tejkogames.models.general.Preference;
import matej.tejkogames.models.general.payload.requests.PreferenceRequest;

@Service
public class PreferenceServiceImpl implements PreferenceService {

    @Autowired
    PreferenceRepository preferenceRepository;

    public Preference getById(UUID id) {
        return preferenceRepository.getById(id);
    }

    public List<Preference> getAll() {
        return preferenceRepository.findAll();
    }

    public void deleteById(UUID id) {
        preferenceRepository.deleteById(id);    
    }

    public void deleteAll() {
        preferenceRepository.deleteAll();
    }

    public Preference updateById(UUID id, PreferenceRequest preferenceRequest) {
        Preference preference = getById(id);
        if (preferenceRequest.getTheme() != null) {
            preference.setTheme(preferenceRequest.getTheme());
        }
        if (preferenceRequest.getVolume() != null) {
            preference.setVolume(preferenceRequest.getVolume());
        }
        return preferenceRepository.save(preference);
    }
    
}
