package pl.patrykdepka.airbattlebackend.DTO;

import com.sun.istack.NotNull;

public class CoordinatesDTO {

    @NotNull
    private int rowNumber;

    @NotNull
    private int columnNumber;

    @NotNull
    private String coordinatesValue;

    public CoordinatesDTO() {
    }

    public CoordinatesDTO(int rowNumber, int columnNumber, String coordinatesValue) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.coordinatesValue = coordinatesValue;
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
