package pl.patrykdepka.airbattlebackend.services;

import pl.patrykdepka.airbattlebackend.models.Message;

import java.util.List;

public interface MessageService {

    void createMessage(Message message);

    List<Message> getLast50Messages();
}
