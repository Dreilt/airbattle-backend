package pl.patrykdepka.airbattlebackend.services;

import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.payload.request.ChangePasswordRequest;
import pl.patrykdepka.airbattlebackend.payload.request.ChangeUserDataRequest;
import pl.patrykdepka.airbattlebackend.payload.request.RegisterRequest;

import java.util.List;

public interface UserService {

    User getOneUserById(long userId);

    void setUserStatus(User user, boolean userStatus);

    User getOneUserByUserName(String userName);

    boolean getExistsUserByUserName(String userName);

    boolean getExistsUserByEmail(String email);

    void createAccount(RegisterRequest registerRequest);

    List<User> getAllUsers();

    void changeUserData(User user, ChangeUserDataRequest changeUserDataRequest);

    void changeUserPassword(User user, ChangePasswordRequest changePasswordRequest);

    void changeUserRole(long userId, int roleId);

    void changeUserLock(long userId, int isBlocked);

    void deleteUser(long userId);
}
