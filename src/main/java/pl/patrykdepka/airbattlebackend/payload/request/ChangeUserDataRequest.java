package pl.patrykdepka.airbattlebackend.payload.request;

import javax.validation.constraints.NotBlank;

public class ChangeUserDataRequest {

    @NotBlank
    private String currentUserName;

    private String newUserName;

    @NotBlank
    private String currentEmail;

    private String newEmail;

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public void setCurrentEmail(String currentEmail) {
        this.currentEmail = currentEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
