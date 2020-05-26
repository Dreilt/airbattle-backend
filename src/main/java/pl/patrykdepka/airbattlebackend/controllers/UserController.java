package pl.patrykdepka.airbattlebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.airbattlebackend.models.Friend;
import pl.patrykdepka.airbattlebackend.models.Game;
import pl.patrykdepka.airbattlebackend.models.Invitation;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.payload.request.ChangePasswordRequest;
import pl.patrykdepka.airbattlebackend.payload.request.ChangeUserDataRequest;
import pl.patrykdepka.airbattlebackend.payload.request.LoginRequest;
import pl.patrykdepka.airbattlebackend.payload.request.RegisterRequest;
import pl.patrykdepka.airbattlebackend.payload.response.JwtResponse;
import pl.patrykdepka.airbattlebackend.payload.response.MessageResponse;
import pl.patrykdepka.airbattlebackend.security.jwt.JwtUtils;
import pl.patrykdepka.airbattlebackend.security.services.UserDetailsImpl;
import pl.patrykdepka.airbattlebackend.services.FriendService;
import pl.patrykdepka.airbattlebackend.services.GameService;
import pl.patrykdepka.airbattlebackend.services.InvitationService;
import pl.patrykdepka.airbattlebackend.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final GameService gameService;
    private final InvitationService invitationService;
    private final FriendService friendService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService,
                          GameService gameService, InvitationService invitationService, FriendService friendService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.gameService = gameService;
        this.invitationService = invitationService;
        this.friendService = friendService;
    }

    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

            User user = userService.getOneUserById(userDetails.getId());

            if (user.isBlocked()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Konto zostało zablokowane przez administratora."));
            }

            userService.setUserStatus(user, true);

            return ResponseEntity.ok(new JwtResponse(userDetails.getId(), accessToken, userDetails.getUsername(), userDetails.getEmail(), roles));
        } catch (BadCredentialsException bce) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nieprawidłowa nazwa użytkownika lub hasło."));
        }
    }

    @PostMapping(value = "/auth/logout")
    public void logout() {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }

        User user = userService.getOneUserByUserName(userName);
        userService.setUserStatus(user, false);
    }

    @PostMapping(value = "/auth/create_account")
    public ResponseEntity<?> createAccount(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.getExistsUserByUserName(registerRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Nazwa użytkownika jest już w użyciu."));
        }

        if (userService.getExistsUserByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Adres email jest zajęty."));
        }

        userService.createAccount(registerRequest);

        return ResponseEntity.ok(new MessageResponse("Konto zostało utworzone pomyślnie!"));
    }

    @GetMapping(value = "/user/users_online_info")
    public int getAllOnlineUsers() {
        List<User> userList = userService.getAllUsers();
        List<User> userOnlineList = new ArrayList<>();
        for (User user : userList) {
            if (user.isUserIsOnline()) {
                userOnlineList.add(user);
            }
        }

        return userOnlineList.size();
    }

    @PostMapping(value = "/user/edit_user_data")
    public ResponseEntity<?> changeUserData(@Valid @RequestBody ChangeUserDataRequest changeUserDataRequest) {
        User user = userService.getOneUserByUserName(changeUserDataRequest.getCurrentUserName());

        if (!changeUserDataRequest.getNewUserName().equals(changeUserDataRequest.getCurrentUserName())) {
            if (userService.getExistsUserByUserName(changeUserDataRequest.getNewUserName())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Ta nazwa użytkownika jest już zajęta."));
            } else {
                userService.changeUserData(user, changeUserDataRequest);
                return ResponseEntity.ok(new MessageResponse("Nazwa użytkownika została zmieniona pomyślnie."));
            }
        }

        if (!changeUserDataRequest.getNewEmail().equals(changeUserDataRequest.getCurrentEmail())) {
            if (userService.getExistsUserByEmail(changeUserDataRequest.getNewEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Ten adres email jest już zajęty."));
            } else {
                userService.changeUserData(user, changeUserDataRequest);
                return ResponseEntity.ok(new MessageResponse("Adres email został zmieniony pomyślnie."));
            }
        }

        return ResponseEntity.ok(new MessageResponse("Dane zostały zaktualizowane."));
    }

    @PostMapping(value = "/user/change_password")
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userService.getOneUserByUserName(changePasswordRequest.getCurrentUserName());
        userService.changeUserPassword(user, changePasswordRequest);

        return ResponseEntity.ok(new MessageResponse("Hasło zostało zmienione pomyślnie."));
    }

    @PostMapping(value = "/user/{userId}=send_invitation")
    public void sendInvitation(@PathVariable(value = "userId") long userId, @RequestBody Invitation invitation) {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User invitationFrom = userService.getOneUserByUserName(userName);
        invitation.setInvitationFrom(invitationFrom);

        User invitationTo = userService.getOneUserById(userId);
        invitation.setInvitationTo(invitationTo);

        invitationService.createInvitation(invitation);
    }

    @GetMapping(value = "/user/get_all_invitations")
    public List<Invitation> getAllInvitations() {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        return invitationService.getAllInvitationsByUser(user);
    }

    @PostMapping(value = "/user/accept_invitation")
    public void acceptInvitation(@RequestBody Long invitationFrom) {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User invitationToUser = userService.getOneUserByUserName(userName);

        invitationService.acceptInvitation(invitationFrom, invitationToUser);
    }

    @GetMapping(value = "/user/check_if_friends/game/{gameId}")
    public int checkIfFriends(@PathVariable(value = "gameId") long gameId) {
        Game game = gameService.getDataGame(gameId);
        User firstPlayer = game.getFirstPlayer();
        User secondPlayer = game.getSecondPlayer();
        int isFriend = 0;

        List<Invitation> invitationList = invitationService.getAllInvitations();
        for (Invitation invitation : invitationList) {
            if (invitation.getInvitationFrom().getId() == firstPlayer.getId() && invitation.getInvitationTo().getId() == secondPlayer.getId()) {
                isFriend = 1;
            }

            if (invitation.getInvitationFrom().getId() == secondPlayer.getId() && invitation.getInvitationTo().getId() == firstPlayer.getId()) {
                isFriend = 1;
            }
        }

        List<Friend> friendList = friendService.getAllFriends();
        for (Friend friend : friendList) {
            if (friend.getFriend_one().getId() == firstPlayer.getId() && friend.getFriend_two().getId() == secondPlayer.getId()) {
                isFriend = 1;
            }

            if (friend.getFriend_one().getId() == secondPlayer.getId() && friend.getFriend_two().getId() == firstPlayer.getId()) {
                isFriend = 1;
            }
        }

        return isFriend;
    }
}
