package pl.patrykdepka.airbattlebackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.patrykdepka.airbattlebackend.models.User;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    User findOneUserById(long userId);

    User findOneByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);

    @Modifying
    @Query(value = "UPDATE user_role SET role_id = :roleId WHERE user_id = :userId", nativeQuery = true)
    void changeUserRole(@Param("userId") Long userId, @Param("roleId") int roleId);
}
