package pl.patrykdepka.airbattlebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.patrykdepka.airbattlebackend.enums.UserRole;
import pl.patrykdepka.airbattlebackend.models.Role;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.payload.request.ChangePasswordRequest;
import pl.patrykdepka.airbattlebackend.payload.request.ChangeUserDataRequest;
import pl.patrykdepka.airbattlebackend.payload.request.RegisterRequest;
import pl.patrykdepka.airbattlebackend.repositories.RoleRepository;
import pl.patrykdepka.airbattlebackend.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getOneUserById(long userId) {
        return userRepository.findOneUserById(userId);
    }

    @Override
    public void setUserStatus(User user, boolean userStatus) {
        user.setUserIsOnline(userStatus);
        userRepository.save(user);
    }

    @Override
    public User getOneUserByUserName(String userName) {
        return userRepository.findOneByUserName(userName);
    }

    @Override
    public boolean getExistsUserByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean getExistsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void createAccount(RegisterRequest registerRequest) {
        User user = new User(registerRequest.getUserName(), registerRequest.getEmail(), encoder.encode(registerRequest.getPassword()));

        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role playerRole = roleRepository.findByName(UserRole.ROLE_PLAYER).orElseThrow(() -> new RuntimeException("Błąd: Rola nie została znaleziona."));
            roles.add(playerRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(UserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Błąd: Rola nie została znaleziona."));
                        roles.add(adminRole);

                        break;
                    default:
                        Role playerRole = roleRepository.findByName(UserRole.ROLE_PLAYER)
                                .orElseThrow(() -> new RuntimeException("Błąd: Rola nie została znaleziona."));
                        roles.add(playerRole);
                }
            });
        }

        user.setRoles(roles);
        user.setBlocked(false);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changeUserData(User user, ChangeUserDataRequest changeUserDataRequest) {
        if (changeUserDataRequest.getNewUserName() != null) {
            user.setUserName(changeUserDataRequest.getNewUserName());
        }

        if (changeUserDataRequest.getNewEmail() != null) {
            user.setEmail(changeUserDataRequest.getNewEmail());
        }

        userRepository.save(user);
    }

    @Override
    public void changeUserPassword(User user, ChangePasswordRequest changePasswordRequest) {
        user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void changeUserRole(long userId, int roleId) {
        userRepository.changeUserRole(userId, roleId);
    }

    @Override
    public void changeUserLock(long userId, int isBlocked) {
        User user = userRepository.findOneUserById(userId);

        if (isBlocked == 1) {
            user.setBlocked(true);
        } else if (isBlocked == 0){
            user.setBlocked(false);
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
