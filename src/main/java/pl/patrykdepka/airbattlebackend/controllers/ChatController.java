package pl.patrykdepka.airbattlebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.airbattlebackend.models.Message;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.services.MessageService;
import pl.patrykdepka.airbattlebackend.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/chat")
public class ChatController {

    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public ChatController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping(value = "/create_message")
    public void createMessage(@Valid @RequestBody Message message) {
        String userName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userName = auth.getName();
        }
        User user = userService.getOneUserByUserName(userName);

        if (message != null) {
            message.setUser(user);
            messageService.createMessage(message);
        }
    }

    @GetMapping(value = "/get_last_50_messages")
    public List<Message> getLast50Messages() {
        List<Message> messageListUnsorted = messageService.getLast50Messages();

        List<Message> messageList = new ArrayList<>();
        if (messageListUnsorted.size() > 0) {
            for (int i = messageListUnsorted.size() - 1; i >= 0; i--) {
                messageList.add(messageListUnsorted.get(i));
            }

        }

        return messageList;
    }
}
