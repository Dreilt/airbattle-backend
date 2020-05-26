package pl.patrykdepka.airbattlebackend.DTO;

import com.sun.istack.NotNull;

import java.util.Date;

public class GameHistoryDTO {

    @NotNull
    private String friend;

    @NotNull
    private String enemy;

    private String difficultyLevel;

    @NotNull
    private Date dateCreated;

    @NotNull
    private String win;

    public GameHistoryDTO() {
    }

    public GameHistoryDTO(String friend, String enemy, String difficultyLevel, Date dateCreated, String win) {
        this.friend = friend;
        this.enemy = enemy;
        this.difficultyLevel = difficultyLevel;
        this.dateCreated = dateCreated;
        this.win = win;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }
}
