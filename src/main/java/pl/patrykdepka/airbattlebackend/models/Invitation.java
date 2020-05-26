package pl.patrykdepka.airbattlebackend.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User invitationFrom;

    @ManyToOne
    private User invitationTo;

    private Date dateCreated;

    public Invitation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getInvitationFrom() {
        return invitationFrom;
    }

    public void setInvitationFrom(User invitationFrom) {
        this.invitationFrom = invitationFrom;
    }

    public User getInvitationTo() {
        return invitationTo;
    }

    public void setInvitationTo(User invitationTo) {
        this.invitationTo = invitationTo;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
