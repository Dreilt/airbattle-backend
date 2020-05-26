package pl.patrykdepka.airbattlebackend.models;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User friend_one;

    @ManyToOne
    private User friend_two;

    public Friend() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getFriend_one() {
        return friend_one;
    }

    public void setFriend_one(User friend_one) {
        this.friend_one = friend_one;
    }

    public User getFriend_two() {
        return friend_two;
    }

    public void setFriend_two(User friend_two) {
        this.friend_two = friend_two;
    }
}
