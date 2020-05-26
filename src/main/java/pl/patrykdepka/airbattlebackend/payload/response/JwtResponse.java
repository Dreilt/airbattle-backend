package pl.patrykdepka.airbattlebackend.payload.response;

import java.util.List;

public class JwtResponse {

    private Long id;
    private String accessToken;
    private String tokenType = "Bearer";
    private String userName;
    private String email;
    private List<String> roles;

    public JwtResponse(Long id, String accessToken, String userName, String email, List<String> roles) {
        this.id = id;
        this.accessToken = accessToken;
        this.userName = userName;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
