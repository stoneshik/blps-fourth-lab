package lab.blps.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lab.blps.security.bd.entities.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    @Transactional
    @Modifying
    @Query(
        "update User user set user.amountRequest = user.amountRequest + :addNumberRequest WHERE user.userId = :userId"
    )
    void addAmountRequest(
        @Param("userId") String userId,
        @Param("addNumberRequest") Integer addNumberRequest
    );

    @Transactional
    @Modifying
    @Query(
        "update User user set user.amountRequest = user.amountRequest - :subNumberRequest WHERE user.userId = :userId"
    )
    void subAmountRequest(
        @Param("userId") String userId,
        @Param("subNumberRequest") Integer subNumberRequest
    );
}
