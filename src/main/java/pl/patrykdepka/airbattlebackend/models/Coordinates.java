package pl.patrykdepka.airbattlebackend.models;

import javax.persistence.*;

@Entity
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rowNumber;

    private int columnNumber;

    private String coordinatesValue;

    private String userName;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public Coordinates() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public String getCoordinatesValue() {
        return coordinatesValue;
    }

    public void setCoordinatesValue(String coordinatesValue) {
        this.coordinatesValue = coordinatesValue;
    }

    public Game getGame() {
        return game;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
