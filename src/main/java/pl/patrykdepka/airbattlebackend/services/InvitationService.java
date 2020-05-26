package pl.patrykdepka.airbattlebackend.services;

import pl.patrykdepka.airbattlebackend.models.Invitation;
import pl.patrykdepka.airbattlebackend.models.User;

import java.util.List;

public interface InvitationService {

    List<Invitation> getAllInvitations();

    List<Invitation> getAllInvitationsByUser(User user);

    void acceptInvitation(Long invitationFrom, User invitationTo);

    void createInvitation(Invitation invitation);
}
