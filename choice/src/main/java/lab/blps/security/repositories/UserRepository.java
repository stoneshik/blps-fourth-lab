package lab.blps.security.repositories;

import lab.blps.security.bd.entities.RoleEnum;
import lab.blps.security.bd.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional<User> findById(Long id);
    Boolean existsByLogin(String login);
    List<User> findAllByRolesName(RoleEnum role);

    @Transactional
    @Modifying
    @Query(
        "update User user set user.amountRequest = user.amountRequest + :addNumberRequest WHERE user.id = :userId"
    )
    void addAmountRequest(
        @Param("userId") Long userId,
        @Param("addNumberRequest") Integer addNumberRequest
    );

    @Transactional
    @Modifying
    @Query(
        "update User user set user.amountRequest = user.amountRequest - :subNumberRequest WHERE user.id = :userId"
    )
    void subAmountRequest(
        @Param("userId") Long userId,
        @Param("subNumberRequest") Integer subNumberRequest
    );
}
