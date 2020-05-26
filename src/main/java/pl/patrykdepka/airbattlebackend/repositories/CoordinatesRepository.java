package pl.patrykdepka.airbattlebackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykdepka.airbattlebackend.models.Coordinates;

import java.util.List;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {

    List<Coordinates> findAllCoordinatesByGameId(Long gameId);
}
