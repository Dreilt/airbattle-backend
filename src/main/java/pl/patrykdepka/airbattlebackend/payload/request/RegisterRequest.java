package pl.patrykdepka.airbattlebackend.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class RegisterRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String email;

    private Set<String> role;

    @NotBlank
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
