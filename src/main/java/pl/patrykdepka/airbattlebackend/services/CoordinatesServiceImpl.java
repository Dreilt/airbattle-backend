package pl.patrykdepka.airbattlebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.patrykdepka.airbattlebackend.DTO.ChangeCoordinatesValueDTO;
import pl.patrykdepka.airbattlebackend.DTO.CoordinatesDTO;
import pl.patrykdepka.airbattlebackend.models.Coordinates;
import pl.patrykdepka.airbattlebackend.models.Game;
import pl.patrykdepka.airbattlebackend.repositories.CoordinatesRepository;

import java.util.List;

@Service
public class CoordinatesServiceImpl implements CoordinatesService {

    private final CoordinatesRepository coordinatesRepository;

    @Autowired
    public CoordinatesServiceImpl(CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }

    @Override
    public void createCoordinatesList(CoordinatesDTO[] coordinatesDTO, String userName, Game game) {
        for (CoordinatesDTO coordinates : coordinatesDTO) {
            Coordinates coordinatesObject = new Coordinates();
            coordinatesObject.setRowNumber(coordinates.getRowNumber());
            coordinatesObject.setColumnNumber(coordinates.getColumnNumber());
            coordinatesObject.setCoordinatesValue(coordinates.getCoordinatesValue());
            coordinatesObject.setUserName(userName);
            coordinatesObject.setGame(game);
            coordinatesRepository.save(coordinatesObject);
        }
    }

    @Override
    public List<Coordinates> getAllCoordinatesByGameId(long gameId) {
        return coordinatesRepository.findAllCoordinatesByGameId(gameId);
    }

    @Override
    public void createCoordinates(ChangeCoordinatesValueDTO changeCoordinatesValueDTO, Game game, String userName) {
        Coordinates coordinates = new Coordinates();
        coordinates.setRowNumber(changeCoordinatesValueDTO.getRowNumber());
        coordinates.setColumnNumber(changeCoordinatesValueDTO.getColumnNumber());
        coordinates.setCoordinatesValue(changeCoordinatesValueDTO.getCoordinatesValue());
        coordinates.setGame(game);
        coordinates.setUserName(userName);

        coordinatesRepository.save(coordinates);
    }
}
