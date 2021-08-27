package matej.tejkogames.interfaces.services;

import java.util.UUID;

import matej.tejkogames.models.yamb.YambChallenge;

public interface YambChallengeService extends ServiceInterface<YambChallenge, UUID> {
    
    public boolean hasPermission(UUID id, String username);

}
