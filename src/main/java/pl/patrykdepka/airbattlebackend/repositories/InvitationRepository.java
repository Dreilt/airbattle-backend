package pl.patrykdepka.airbattlebackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.patrykdepka.airbattlebackend.models.Invitation;
import pl.patrykdepka.airbattlebackend.models.User;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByInvitationFromAndInvitationTo(User invitationFromUser, User invitationToUser);

    List<Invitation> findAllByInvitationTo(User user);
}
