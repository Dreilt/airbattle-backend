package pl.patrykdepka.airbattlebackend.DTO;

import pl.patrykdepka.airbattlebackend.models.User;

public class ChangeCoordinatesValueDTO {

    private int id;
    private User user;
    private int rowNumber;
    private int columnNumber;
    private String coordinatesValue;

    public ChangeCoordinatesValueDTO() {
    }

    public ChangeCoordinatesValueDTO(int id, User user, int rowNumber, int columnNumber, String coordinatesValue) {
        this.id = id;
        this.user = user;
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.coordinatesValue = coordinatesValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
