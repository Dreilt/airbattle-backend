package pl.patrykdepka.airbattlebackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.patrykdepka.airbattlebackend.models.Friend;
import pl.patrykdepka.airbattlebackend.models.Invitation;
import pl.patrykdepka.airbattlebackend.models.User;
import pl.patrykdepka.airbattlebackend.repositories.FriendRepository;
import pl.patrykdepka.airbattlebackend.repositories.InvitationRepository;
import pl.patrykdepka.airbattlebackend.repositories.UserRepository;

import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Autowired
    public InvitationServiceImpl(InvitationRepository invitationRepository, UserRepository userRepository, FriendRepository friendRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    @Override
    public void createInvitation(Invitation invitation) {
        invitationRepository.save(invitation);
    }

    @Override
    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }

    @Override
    public void acceptInvitation(Long invitationFrom, User invitationToUser) {
        Friend friend = new Friend();
        User invitationFromUser = userRepository.findOneUserById(invitationFrom);
        friend.setFriend_one(invitationFromUser);
        friend.setFriend_two(invitationToUser);
        friendRepository.save(friend);

        List<Invitation> invitationList = invitationRepository.findAllByInvitationFromAndInvitationTo(invitationFromUser, invitationToUser);
        for (Invitation invitation : invitationList) {
            invitationRepository.delete(invitation);
        }
    }

    @Override
    public List<Invitation> getAllInvitationsByUser(User user) {
        return invitationRepository.findAllByInvitationTo(user);
    }
}
