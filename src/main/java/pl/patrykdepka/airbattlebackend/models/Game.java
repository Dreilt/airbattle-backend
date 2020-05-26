package pl.patrykdepka.airbattlebackend.models;

import pl.patrykdepka.airbattlebackend.enums.GameStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User firstPlayer;
    private int firstPlayerPoints;
    private String firstPlayerStatus;

    private String enemy;
    private String difficultyLevel;

    @ManyToOne
    private User secondPlayer;
    private int secondPlayerPoints;
    private String secondPlayerStatus;

    private Date dateCreated;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToOne
    private User currentPlayer;

    public Game() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(User firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public int getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    public void setFirstPlayerPoints(int firstPlayerPoints) {
        this.firstPlayerPoints = firstPlayerPoints;
    }

    public String getFirstPlayerStatus() {
        return firstPlayerStatus;
    }

    public void setFirstPlayerStatus(String firstPlayerStatus) {
        this.firstPlayerStatus = firstPlayerStatus;
    }

    public String getEnemy() {
        return enemy;
    }

    public void setEnemy(String enemy) {
        this.enemy = enemy;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public User getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(User secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int getSecondPlayerPoints() {
        return secondPlayerPoints;
    }

    public void setSecondPlayerPoints(int secondPlayerPoints) {
        this.secondPlayerPoints = secondPlayerPoints;
    }

    public String getSecondPlayerStatus() {
        return secondPlayerStatus;
    }

    public void setSecondPlayerStatus(String secondPlayerStatus) {
        this.secondPlayerStatus = secondPlayerStatus;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(User currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
