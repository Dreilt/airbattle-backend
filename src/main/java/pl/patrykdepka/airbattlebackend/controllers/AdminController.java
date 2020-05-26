package pl.patrykdepka.airbattlebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.airbattlebackend.DTO.UserDTO;
import pl.patrykdepka.airbattlebackend.enums.UserRole;
import pl.patrykdepka.airbattlebackend.models.Role;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.services.UserService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user : userList) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setUserName(user.getUserName());

            if (user.isBlocked()) {
                userDTO.setBlocked(1);
            } else {
                userDTO.setBlocked(0);
            }

            Role userRole = new Role();
            int roleId = user.getRoles().iterator().next().getId();
            userRole.setId(roleId);
            UserRole nameRole = user.getRoles().iterator().next().getName();
            userRole.setName(nameRole);
            userDTO.setRole(userRole);

            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

    @PostMapping(value = "/users/user/{userId}=change_role")
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserRole(@PathVariable(value = "userId") long userId, @RequestBody int roleId) {
        userService.changeUserRole(userId, roleId);
    }

    @PostMapping(value = "/users/user/{userId}=change_lock")
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserLock(@PathVariable(value = "userId") long userId, @RequestBody int isBlocked) {
        userService.changeUserLock(userId, isBlocked);
    }

    @DeleteMapping(value = "/users/user/{userId}=delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable(value = "userId") long userId) {
        userService.deleteUser(userId);
    }
}
