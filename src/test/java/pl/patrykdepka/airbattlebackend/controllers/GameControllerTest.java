package pl.patrykdepka.airbattlebackend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.patrykdepka.airbattlebackend.DTO.NewGameDataDTO;
import pl.patrykdepka.airbattlebackend.enums.GameStatus;
import pl.patrykdepka.airbattlebackend.models.Game;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.repositories.FriendRepository;
import pl.patrykdepka.airbattlebackend.repositories.GameRepository;
import pl.patrykdepka.airbattlebackend.repositories.RoleRepository;
import pl.patrykdepka.airbattlebackend.repositories.UserRepository;
import pl.patrykdepka.airbattlebackend.services.GameServiceImpl;
import pl.patrykdepka.airbattlebackend.services.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GameControllerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder encoder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    FriendRepository friendRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @InjectMocks
    GameServiceImpl gameServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userServiceImpl = new UserServiceImpl(userRepository, encoder, roleRepository);
        gameServiceImpl = new GameServiceImpl(gameRepository, friendRepository);
    }

    @Test
    void createNewGame() {
        when(userRepository.findOneByUserName("Administrator")).thenReturn(getUserAdmin());
        when(gameRepository.save(getGame(getUserAdmin()))).thenReturn(getGame(getUserAdmin()));

        when(userRepository.findOneByUserName("Test")).thenReturn(getUserTest());
        when(gameRepository.save(getGame(getUserTest()))).thenReturn(getGame(getUserTest()));

        gameServiceImpl.createNewGame(getUserAdmin(), getDataGame());
        gameServiceImpl.createNewGame(getUserTest(), getDataGame());
        verify(gameRepository, times(2)).save(any(Game.class));
    }

    @Test
    void getGamesToJoinList() {
        when(userRepository.findOneByUserName("Test")).thenReturn(getUserTest());
        when(gameRepository.findByEnemyAndGameStatus("P", GameStatus.WAITS_FOR_PLAYER).stream()
                .filter(game -> !game.getFirstPlayer().getUserName().equals(getUserTest().getUserName())).collect(Collectors.toList()))
                .thenReturn(getGameList());

        List<Game> gameList = gameServiceImpl.getGamesToJoinList(getUserTest());

        assertThat(gameList).hasSize(1);
    }

    NewGameDataDTO getDataGame() {
        NewGameDataDTO newGameDataDTO = new NewGameDataDTO();
        newGameDataDTO.setId(1);
        newGameDataDTO.setEnemy("P");
        newGameDataDTO.setDifficultyLevel(null);

        return newGameDataDTO;
    }

    User getUserAdmin() {
        User user = new User();
        user.setUserName("Administrator");
        user.setEmail("administrator@administrator.pl");

        return user;
    }

    User getUserTest() {
        User user = new User();
        user.setUserName("Test");
        user.setEmail("test@test.pl");

        return user;
    }

    Game getGame(User user) {
        Game game = new Game();
        game.setId(1L);
        game.setFirstPlayer(user);
        game.setFirstPlayerPoints(0);
        game.setFirstPlayerStatus("JTG");
        game.setEnemy("P");
        game.setDifficultyLevel(null);
        game.setSecondPlayer(null);
        game.setSecondPlayerPoints(0);
        game.setSecondPlayerStatus(null);
        game.setDateCreated(null);
        game.setGameStatus(GameStatus.WAITS_FOR_PLAYER);
        game.setCurrentPlayer(user);

        return game;
    }

    List<Game> getGameList() {
        List<Game> list = new ArrayList<>();
        Game game1 = getGame(getUserAdmin());
        list.add(game1);
        Game game2 = getGame(getUserTest());
        list.add(game2);

        List<Game> gameList = list.stream().filter(game -> !game.getFirstPlayer().getUserName().equals(getUserTest().getUserName())).collect(Collectors.toList());

        return gameList;
    }
}