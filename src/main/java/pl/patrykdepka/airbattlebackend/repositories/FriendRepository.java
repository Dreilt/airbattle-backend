package pl.patrykdepka.airbattlebackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.patrykdepka.airbattlebackend.models.Friend;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "SELECT * FROM friends f WHERE f.friend_one_id = :userId OR f.friend_two_id = :userId", nativeQuery = true)
    List<Friend> findFriends(@Param("userId") Long userId);
}
