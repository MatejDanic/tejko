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
            roleRepo.saveAll(Arrays.asList(new Role(1, "ADMIN", ""), new Role(2, "USER", ""), new Role(3, "MODERATOR", "")));
        }

        // if (columnTypeRepo.findAll().size() == 0) {
        //     columnTypeRepo.saveAll(Arrays.asList(new ColumnType(1, "DOWNWARDS"), new ColumnType(2, "UPWARDS"),
        //             new ColumnType(3, "ANY_DIRECTION"), new ColumnType(4, "ANNOUNCEMENT")));
        // }

        // if (boxTypeRepo.findAll().size() == 0) {
        //     boxTypeRepo.saveAll(Arrays.asList(new BoxType(1, "ONES"), new BoxType(2, "TWOS"), new BoxType(3, "THREES"),
        //             new BoxType(4, "FOURS"), new BoxType(5, "FIVES"), new BoxType(6, "SIXES"), new BoxType(7, "MAX"),
        //             new BoxType(8, "MIN"), new BoxType(9, "TRIPS"), new BoxType(10, "STRAIGHT"), new BoxType(11, "FULL"),
        //             new BoxType(12, "POKER"), new BoxType(13, "JAMB")));
        // }
    }
}
