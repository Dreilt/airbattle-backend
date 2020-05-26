package pl.patrykdepka.airbattlebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.patrykdepka.airbattlebackend.models.Friend;
import pl.patrykdepka.airbattlebackend.repositories.FriendRepository;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public List<Friend> getAllFriends() {
        return friendRepository.findAll();
    }
}
