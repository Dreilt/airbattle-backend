package pl.patrykdepka.airbattlebackend.DTO;

public class NewGameDataDTO {

    private int id;
    private String enemy;
    private String difficultyLevel;

    public NewGameDataDTO() {
    }

    public NewGameDataDTO(int id, String enemy, String difficultyLevel) {
        this.id = id;
        this.enemy = enemy;
        this.difficultyLevel = difficultyLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
