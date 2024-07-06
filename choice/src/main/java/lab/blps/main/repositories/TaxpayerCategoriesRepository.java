package lab.blps.main.repositories;

import lab.blps.main.bd.entites.TaxpayerCategories;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaxpayerCategoriesRepository extends JpaRepository<TaxpayerCategories, Long> {
    @Transactional
    @Modifying
    @Query(
        "update TaxpayerCategories tc set tc.taxpayerCategoryEnum = :taxpayerCategoryEnum WHERE tc.id = :taxRegimeId"
    )
    void update(
        @Param("taxRegimeId") Long taxRegimeId,
        @Param("taxpayerCategoryEnum") TaxpayerCategoryEnum taxpayerCategoryEnum
    );
}
