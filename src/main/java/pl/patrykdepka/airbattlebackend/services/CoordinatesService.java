package pl.patrykdepka.airbattlebackend.services;

import pl.patrykdepka.airbattlebackend.DTO.ChangeCoordinatesValueDTO;
import pl.patrykdepka.airbattlebackend.DTO.CoordinatesDTO;
import pl.patrykdepka.airbattlebackend.models.Coordinates;
import pl.patrykdepka.airbattlebackend.models.Game;

import java.util.List;

public interface CoordinatesService {

    void createCoordinatesList(CoordinatesDTO[] coordinatesDTO, String userName, Game game);

    List<Coordinates> getAllCoordinatesByGameId(long gameId);

    void createCoordinates(ChangeCoordinatesValueDTO changeCoordinatesValueDTO, Game game, String userName);
}
