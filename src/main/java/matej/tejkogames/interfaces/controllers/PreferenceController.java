package matej.tejkogames.interfaces.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import matej.tejkogames.models.general.Preference;
import matej.tejkogames.models.general.payload.requests.PreferenceRequest;

public interface PreferenceController extends ControllerInterface<Preference, UUID> {
    
    public ResponseEntity<Preference> updateById(UUID id, PreferenceRequest preferenceRequest);

}
