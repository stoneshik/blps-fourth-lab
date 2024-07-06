package lab.blps.main.repositories;

import lab.blps.main.bd.entites.TaxFeatures;
import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaxFeaturesRepository extends JpaRepository<TaxFeatures, Long> {
    @Transactional
    @Modifying
    @Query(
        "update TaxFeatures tf set tf.taxFeatureEnum = :taxFeatureEnum WHERE tf.id = :taxRegimeId"
    )
    void update(
        @Param("taxRegimeId") Long taxRegimeId,
        @Param("taxFeatureEnum") TaxFeatureEnum taxFeatureEnum
    );
}

