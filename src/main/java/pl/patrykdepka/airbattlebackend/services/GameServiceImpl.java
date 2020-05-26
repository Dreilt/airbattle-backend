package pl.patrykdepka.airbattlebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.airbattlebackend.DTO.GameHistoryDTO;
import pl.patrykdepka.airbattlebackend.DTO.NewGameDataDTO;
import pl.patrykdepka.airbattlebackend.enums.GameStatus;
import pl.patrykdepka.airbattlebackend.models.Friend;
import pl.patrykdepka.airbattlebackend.models.Game;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.repositories.FriendRepository;
import pl.patrykdepka.airbattlebackend.repositories.GameRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final FriendRepository friendRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, FriendRepository friendRepository) {
        this.gameRepository = gameRepository;
        this.friendRepository = friendRepository;
    }

    @Override
    public Game createNewGame(User user, NewGameDataDTO newGameDataDTO) {
        Game game = new Game();

        game.setFirstPlayer(user);
        game.setFirstPlayerPoints(0);
        game.setFirstPlayerStatus("JTG");

        game.setEnemy(newGameDataDTO.getEnemy());
        if (newGameDataDTO.getDifficultyLevel() != null) {
            game.setDifficultyLevel(newGameDataDTO.getDifficultyLevel());
        }

        game.setDateCreated(new Date());
        game.setGameStatus(newGameDataDTO.getEnemy().equals("P") ? GameStatus.WAITS_FOR_PLAYER : GameStatus.DRAW_COORDINATES);
        game.setCurrentPlayer(user);
        gameRepository.save(game);

        return game;
    }

    @Override
    public List<Game> getGamesToJoinList(User user) {
        return gameRepository.findByEnemyAndGameStatus("P", GameStatus.WAITS_FOR_PLAYER).stream().filter(game -> game.getFirstPlayer() != user).collect(Collectors.toList());
    }

    @Override
    public Game joinGame(User user, Long gameId) {
        Game game = gameRepository.findOneGameById(gameId);

        game.setSecondPlayer(user);
        game.setSecondPlayerPoints(0);
        game.setSecondPlayerStatus("JTG");
        game.setGameStatus(GameStatus.DRAW_COORDINATES);
        gameRepository.save(game);

        return game;
    }

    @Override
    public Game getDataGame(Long gameId) {
        return gameRepository.findOneGameById(gameId);
    }

    @Override
    public void changePlayerStatus(User user, String playerStatus, long gameId) {
        Game game = gameRepository.findOneGameById(gameId);

        if (user.getId() == game.getFirstPlayer().getId()) {
            game.setFirstPlayerStatus(playerStatus);
        } else {
            game.setSecondPlayerStatus(playerStatus);
        }

        gameRepository.save(game);
    }

    @Override
    public void changeComputerPlayerStatus(Game game) {
        game.setSecondPlayerStatus("R");
        gameRepository.save(game);
    }

    @Override
    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<GameHistoryDTO> getUserGameHistory(User user) {
        List<Game> gameList = gameRepository.findByGameStatusOrderByDateCreated(GameStatus.GAME_FINISHED);

        List<GameHistoryDTO> gameHistory = new ArrayList<>();
        for (Game game : gameList) {
            if (game.getFirstPlayer().getId() == user.getId() || (game.getSecondPlayer() != null && game.getSecondPlayer().getId() == user.getId())) {
                GameHistoryDTO gameElement = new GameHistoryDTO();

                if (game.getSecondPlayer() != null && game.getFirstPlayer().getId() == user.getId()) {
                    gameElement.setFriend(game.getFirstPlayer().getUserName());
                    gameElement.setEnemy(game.getSecondPlayer().getUserName());

                    if (game.getFirstPlayerPoints() == 20) {
                        gameElement.setWin("T");
                    } else {
                        gameElement.setWin("N");
                    }
                } else if (game.getSecondPlayer() != null && game.getSecondPlayer().getId() == user.getId()) {
                    gameElement.setFriend(game.getSecondPlayer().getUserName());
                    gameElement.setEnemy(game.getFirstPlayer().getUserName());

                    if (game.getSecondPlayerPoints() == 20) {
                        gameElement.setWin("T");
                    } else {
                        gameElement.setWin("N");
                    }
                } else {
                    gameElement.setFriend(game.getFirstPlayer().getUserName());
                    gameElement.setEnemy("Komputer");

                    if (game.getFirstPlayerPoints() == 20) {
                        gameElement.setWin("T");
                    } else {
                        gameElement.setWin("N");
                    }
                }

                if (game.getDifficultyLevel() != null) {
                    gameElement.setDifficultyLevel(game.getDifficultyLevel());
                } else {
                    gameElement.setDifficultyLevel("");
                }

                gameElement.setDateCreated(game.getDateCreated());

                gameHistory.add(gameElement);
            }
        }

        return gameHistory;
    }

    @Override
    public List<GameHistoryDTO> getFriendsGameHistory(User user) {
        List<Game> gameList = gameRepository.findByGameStatusOrderByDateCreated(GameStatus.GAME_FINISHED);

        List<Long> idList = new ArrayList<>();
        List<Friend> friendList = friendRepository.findFriends(user.getId());
        for (Friend friend : friendList) {
            if (friend.getFriend_one().getId() == user.getId()) {
                idList.add(friend.getFriend_two().getId());
            } else {
                idList.add(friend.getFriend_one().getId());
            }
        }

        List<GameHistoryDTO> gameHistory = new ArrayList<>();
        for (Game game : gameList) {
            for (Long id : idList) {
                if (game.getFirstPlayer().getId() == id || (game.getSecondPlayer() != null && game.getSecondPlayer().getId() == id)) {
                    GameHistoryDTO gameElement = new GameHistoryDTO();

                    if (game.getSecondPlayer() != null && game.getFirstPlayer().getId() == id) {
                        gameElement.setFriend(game.getFirstPlayer().getUserName());
                        gameElement.setEnemy(game.getSecondPlayer().getUserName());

                        if (game.getFirstPlayerPoints() == 20) {
                            gameElement.setWin("T");
                        } else {
                            gameElement.setWin("N");
                        }
                    } else if (game.getSecondPlayer() != null && game.getSecondPlayer().getId() == id) {
                        gameElement.setFriend(game.getSecondPlayer().getUserName());
                        gameElement.setEnemy(game.getFirstPlayer().getUserName());

                        if (game.getSecondPlayerPoints() == 20) {
                            gameElement.setWin("T");
                        } else {
                            gameElement.setWin("N");
                        }
                    } else {
                        gameElement.setFriend(game.getFirstPlayer().getUserName());
                        gameElement.setEnemy("Komputer");

                        if (game.getFirstPlayerPoints() == 20) {
                            gameElement.setWin("T");
                        } else {
                            gameElement.setWin("N");
                        }
                    }

                    if (game.getDifficultyLevel() != null) {
                        gameElement.setDifficultyLevel(game.getDifficultyLevel());
                    } else {
                        gameElement.setDifficultyLevel("");
                    }

                    gameElement.setDateCreated(game.getDateCreated());

                    gameHistory.add(gameElement);
                }
            }
        }

        return gameHistory;
    }
}
