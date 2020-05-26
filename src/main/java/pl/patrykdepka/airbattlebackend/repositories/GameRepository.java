package pl.patrykdepka.airbattlebackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykdepka.airbattlebackend.enums.GameStatus;
import pl.patrykdepka.airbattlebackend.models.Game;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByEnemyAndGameStatus(String enemyType, GameStatus gameStatus);

    Game findOneGameById(Long gameId);

    List<Game> findByGameStatusOrderByDateCreated(GameStatus gameStatus);
}
