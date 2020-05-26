package pl.patrykdepka.airbattlebackend.services;

import pl.patrykdepka.airbattlebackend.DTO.GameHistoryDTO;
import pl.patrykdepka.airbattlebackend.DTO.NewGameDataDTO;
import pl.patrykdepka.airbattlebackend.models.Game;
import pl.patrykdepka.airbattlebackend.models.User;

import java.util.List;

public interface GameService {

    Game createNewGame(User user, NewGameDataDTO newGameDataDTO);

    List<Game> getGamesToJoinList(User user);

    Game joinGame(User user, Long gameId);

    Game getDataGame(Long gameId);

    void changePlayerStatus(User user, String playerStatus, long gameId);

    void changeComputerPlayerStatus(Game game);

    void updateGame(Game game);

    List<GameHistoryDTO> getUserGameHistory(User user);

    List<GameHistoryDTO> getFriendsGameHistory(User user);
}
