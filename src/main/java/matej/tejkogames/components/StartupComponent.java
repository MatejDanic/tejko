package matej.tejkogames.components;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import matej.tejkogames.api.repositories.RoleRepository;
import matej.tejkogames.models.general.Role;

@Component
public class StartupComponent implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleRepo.findAll().size() == 0) {
            roleRepo.saveAll(
                    Arrays.asList(new Role(1, "ADMIN", ""), new Role(2, "USER", ""), new Role(3, "MODERATOR", "")));
        }
    }
}
