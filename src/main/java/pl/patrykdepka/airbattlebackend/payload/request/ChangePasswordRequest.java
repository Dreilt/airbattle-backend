package pl.patrykdepka.airbattlebackend.payload.request;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequest {

    @NotBlank
    private String currentUserName;

    @NotBlank
    private String newPassword;

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
