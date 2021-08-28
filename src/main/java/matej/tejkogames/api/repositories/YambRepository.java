package matej.tejkogames.api.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import matej.tejkogames.models.yamb.Yamb;
import matej.tejkogames.models.yamb.YambType;

public interface YambRepository extends JpaRepository<Yamb, UUID> {

    public List<Yamb> findAllByTypeAndUserId(UUID id, YambType type);

}
