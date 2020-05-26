package pl.patrykdepka.airbattlebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.airbattlebackend.DTO.ChangeCoordinatesValueDTO;
import pl.patrykdepka.airbattlebackend.DTO.CoordinatesDTO;
import pl.patrykdepka.airbattlebackend.DTO.GameHistoryDTO;
import pl.patrykdepka.airbattlebackend.DTO.NewGameDataDTO;
import pl.patrykdepka.airbattlebackend.enums.GameStatus;
import pl.patrykdepka.airbattlebackend.models.Coordinates;
import pl.patrykdepka.airbattlebackend.models.Game;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.services.CoordinatesService;
import pl.patrykdepka.airbattlebackend.services.GameService;
import pl.patrykdepka.airbattlebackend.services.UserService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/game")
public class GameController {

    private final UserService userService;
    private final GameService gameService;
    private final CoordinatesService coordinatesService;

    @Autowired
    public GameController(UserService userService, GameService gameService, CoordinatesService coordinatesService) {
        this.userService = userService;
        this.gameService = gameService;
        this.coordinatesService = coordinatesService;
    }

    @PostMapping(value = "/create_game")
    public Game createNewGame(@RequestBody NewGameDataDTO newGameDataDTO) {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        return gameService.createNewGame(user, newGameDataDTO);
    }

    @GetMapping(value = "/list")
    public List<Game> getGamesToJoinList() {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        return gameService.getGamesToJoinList(user);
    }

    @PostMapping(value = "/{gameId}=join")
    public Game joinGame(@PathVariable(value = "gameId") long gameId) {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        return gameService.joinGame(user, gameId);
    }

    @GetMapping(value = "/{gameId}")
    public Game getDataGame(@PathVariable(value = "gameId") long gameId) {
        return gameService.getDataGame(gameId);
    }

    @PostMapping(value = "/{gameId}=change_player_status")
    public void changePlayerStatus(@PathVariable(value = "gameId") long gameId, @RequestBody String playerStatus) {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        gameService.changePlayerStatus(user, playerStatus, gameId);
    }

    @PostMapping(value = "/{gameId}=play_game")
    public void playGame(@PathVariable(value = "gameId") long gameId, @RequestBody CoordinatesDTO[] coordinatesDTO) {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        Game game = gameService.getDataGame(gameId);
        if (user.getId() == game.getFirstPlayer().getId()) {
            game.setFirstPlayerStatus("R");
        } else {
            game.setSecondPlayerStatus("R");
        }
        if ("R".equals(game.getFirstPlayerStatus()) && "R".equals(game.getSecondPlayerStatus())) {
            game.setGameStatus(GameStatus.IN_PROGRESS);
        }

        coordinatesService.createCoordinatesList(coordinatesDTO, user.getUserName(), game);
    }

    @GetMapping(value = "/{gameId}=get_all_coordinates")
    public List<Coordinates> getAllCoordinates(@PathVariable(value = "gameId") long gameId) {
        return coordinatesService.getAllCoordinatesByGameId(gameId);
    }

    @PostMapping(value = "/{gameId}=change_value_at_coordinates")
    public void changeCoordinatesValue(@PathVariable(value = "gameId") long gameId, @RequestBody ChangeCoordinatesValueDTO changeCoordinatesValueDTO) {
        int rowNumber = changeCoordinatesValueDTO.getRowNumber();
        int columnNumber = changeCoordinatesValueDTO.getColumnNumber();

        List<Coordinates> coordinatesList = coordinatesService.getAllCoordinatesByGameId(gameId);
        for (Coordinates coordinates : coordinatesList) {
            if (coordinates.getRowNumber() == rowNumber && coordinates.getColumnNumber() == columnNumber) {
                if (!coordinates.getUserName().equals(changeCoordinatesValueDTO.getUser().getUserName())) {
                    coordinates.setCoordinatesValue(changeCoordinatesValueDTO.getCoordinatesValue());
                }
            }
        }

        Game game = gameService.getDataGame(gameId);

        String userName;
        if ("C".equals(game.getEnemy())) {
            userName = "Computer";
        } else if (changeCoordinatesValueDTO.getUser().getId().equals(game.getFirstPlayer().getId())) {
            userName = game.getSecondPlayer().getUserName();
        } else {
            userName = game.getFirstPlayer().getUserName();
        }

        if (changeCoordinatesValueDTO.getCoordinatesValue().equals("P")) {
            coordinatesService.createCoordinates(changeCoordinatesValueDTO, game, userName);
        }

        if (!changeCoordinatesValueDTO.getCoordinatesValue().equals("P")) {
            if (game.getFirstPlayer().getId() == changeCoordinatesValueDTO.getUser().getId()) {
                int playerPoints = game.getFirstPlayerPoints();
                playerPoints++;
                game.setFirstPlayerPoints(playerPoints);
            } else if (game.getSecondPlayer().getId() == changeCoordinatesValueDTO.getUser().getId()) {
                int playerPoints = game.getSecondPlayerPoints();
                playerPoints++;
                game.setSecondPlayerPoints(playerPoints);
            }
        }

        if (game.getFirstPlayerPoints() == 20 || game.getSecondPlayerPoints() == 20) {
            game.setGameStatus(GameStatus.GAME_FINISHED);
        }

        if (game.getGameStatus() != GameStatus.GAME_FINISHED) {
            if (game.getSecondPlayer() != null) {
                if (game.getCurrentPlayer().getId() == game.getFirstPlayer().getId()) {
                    game.setCurrentPlayer(game.getSecondPlayer());
                } else if (game.getCurrentPlayer().getId() == game.getSecondPlayer().getId()) {
                    game.setCurrentPlayer(game.getFirstPlayer());
                }
            }
        }

        gameService.updateGame(game);
    }

    @PostMapping(value = "/{gameId}=play_game_computer")
    public void playGameComputer(@PathVariable(value = "gameId") long gameId, @RequestBody CoordinatesDTO[] coordinatesDTO) {
        Game game = gameService.getDataGame(gameId);

        coordinatesService.createCoordinatesList(coordinatesDTO, "Computer", game);

        gameService.changeComputerPlayerStatus(game);
    }

    @PostMapping(value = "/{gameId}=change_value_at_coordinates_computer")
    public void changeValueAtCoordinatesComputer(@PathVariable(value = "gameId") long gameId, @RequestBody ChangeCoordinatesValueDTO changeCoordinatesValueDTO) {
        int rowNumber = changeCoordinatesValueDTO.getRowNumber();
        int columnNumber = changeCoordinatesValueDTO.getColumnNumber();

        Game game = gameService.getDataGame(gameId);

        List<Coordinates> coordinatesList = coordinatesService.getAllCoordinatesByGameId(gameId);
        for (Coordinates coordinates : coordinatesList) {
            if (coordinates.getRowNumber() == rowNumber && coordinates.getColumnNumber() == columnNumber) {
                if (coordinates.getUserName().equals(game.getFirstPlayer().getUserName())) {
                    coordinates.setCoordinatesValue(changeCoordinatesValueDTO.getCoordinatesValue());
                }
            }
        }

        if (changeCoordinatesValueDTO.getCoordinatesValue().equals("P")) {
            coordinatesService.createCoordinates(changeCoordinatesValueDTO, game, game.getFirstPlayer().getUserName());
        }

        if (!changeCoordinatesValueDTO.getCoordinatesValue().equals("P")) {
            int computerPoints = game.getSecondPlayerPoints();
            computerPoints++;
            game.setSecondPlayerPoints(computerPoints);
        }

        if (game.getSecondPlayerPoints() == 20) {
            game.setCurrentPlayer(null);
            game.setGameStatus(GameStatus.GAME_FINISHED);
        }

        gameService.updateGame(game);
    }

    @GetMapping(value = "/get_user_game_history")
    public List<GameHistoryDTO> getUserGameHistory() {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        return gameService.getUserGameHistory(user);
    }

    @GetMapping(value = "/get_friends_game_history")
    public List<GameHistoryDTO> getFriendsGameHistory() {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        return gameService.getFriendsGameHistory(user);
    }
}
