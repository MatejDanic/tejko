package matej.tejkogames.components;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.api.repositories.TejkoGameRepository;
import matej.tejkogames.models.general.Role;
import matej.tejkogames.models.general.TejkoGame;

@Component
public class StartupComponent implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TejkoGameRepository tejkoGamesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleRepository.findAll().size() == 0) {
            roleRepository.saveAll(
                    Arrays.asList(new Role(1, "ADMIN", ""), new Role(2, "USER", ""), new Role(3, "MODERATOR", "")));
        }
        
        if (tejkoGamesRepository.findAll().size() == 0) {
            tejkoGamesRepository.saveAll(
                    Arrays.asList(new TejkoGame(1, "Yamb", "")));
        }

    }
}
