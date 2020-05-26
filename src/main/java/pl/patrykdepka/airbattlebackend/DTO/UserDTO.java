package pl.patrykdepka.airbattlebackend.DTO;

import pl.patrykdepka.airbattlebackend.models.Role;

public class UserDTO {

    private Long id;
    private String userName;
    private String email;

    private int blocked;

    private Role role;

    public UserDTO() {
    }

    public UserDTO(Long id, String userName, String email, int blocked, Role role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.blocked = blocked;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
