package pl.patrykdepka.airbattlebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.patrykdepka.airbattlebackend.models.Message;
import pl.patrykdepka.airbattlebackend.repositories.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void createMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> getLast50Messages() {
        return messageRepository.findTop50ByOrderByDateCreatedDesc();
    }
}
