package lab.blps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lab.blps.bd.entites.user.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query(
        "update User user set user.amountRequest = user.amountRequest + :addNumberRequest"
    )
    void addAllUserAmountRequest(@Param("addNumberRequest") Integer addNumberRequest);

    @Transactional
    @Modifying
    @Query(
        "update User user set user.amountRequest = user.amountRequest - :subNumberRequest"
    )
    void subAllUserAmountRequest(@Param("subNumberRequest") Integer subNumberRequest);

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
