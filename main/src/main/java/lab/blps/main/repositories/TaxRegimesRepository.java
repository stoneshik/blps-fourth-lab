package lab.blps.main.repositories;

import lab.blps.main.bd.entites.TaxFeatures;
import lab.blps.main.bd.entites.TaxRegimes;
import lab.blps.main.bd.entites.TaxpayerCategories;
import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TaxRegimesRepository extends JpaRepository<TaxRegimes, Long> {
    @Query(
            "from TaxRegimes tr where " +
            "(tr.maxAnnualIncomeThousands is null or :maxAnnualIncomeThousands <= tr.maxAnnualIncomeThousands) " +
            "and (tr.maxNumberEmployees is null or :maxNumberEmployees <= tr.maxNumberEmployees)"
    )
    List<TaxRegimes> findByMaxAnnualIncomeThousandsAndMaxNumberEmployees(
            @Param("maxAnnualIncomeThousands") Long maxAnnualIncomeThousands,
            @Param("maxNumberEmployees") Long maxNumberEmployees
    );

    @Query(
            "from TaxRegimes tr where " +
            "(tr.maxAnnualIncomeThousands is null or :maxAnnualIncomeThousands <= tr.maxAnnualIncomeThousands) " +
            "and (tr.maxNumberEmployees is null or :maxNumberEmployees <= tr.maxNumberEmployees)" +
            "and tr.id in :filteredTaxRegimeIds"
    )
    List<TaxRegimes> findByMaxAnnualIncomeThousandsAndMaxNumberEmployeesAndFilteredTaxRegimeIds(
            @Param("maxAnnualIncomeThousands") Long maxAnnualIncomeThousands,
            @Param("maxNumberEmployees") Long maxNumberEmployees,
            @Param("filteredTaxRegimeIds") List<Long> filteredTaxRegimeIds
    );

    @Query(
            "from TaxpayerCategories tc where tc.taxpayerCategoryEnum in :taxpayerCategories"
    )
    List<TaxpayerCategories> findTaxpayerCategories(
            @Param("taxpayerCategories") List<TaxpayerCategoryEnum> taxpayerCategories
    );

    @Query(
            "from TaxFeatures tf where tf.taxFeatureEnum in :taxFeatures"
    )
    List<TaxFeatures> findTaxFeatures(
            @Param("taxFeatures") List<TaxFeatureEnum> taxFeatures
    );

    @Query(
            "from TaxFeatures tf where tf.taxFeatureEnum in :taxFeatures and tf.taxRegimes.id in :filteredTaxRegimeIds"
    )
    List<TaxFeatures> findTaxFeaturesByFilteredTaxRegimeIds(
            @Param("taxFeatures") List<TaxFeatureEnum> taxFeatures,
            @Param("filteredTaxRegimeIds") List<Long> filteredTaxRegimeIds
    );

    @Transactional
    @Modifying
    @Query(
        "update TaxRegimes tr set tr.title = :title, " +
            "tr.description = :description, " +
            "tr.maxAnnualIncomeThousands = :max_annual_income_thousands, " +
            "tr.maxNumberEmployees = :max_number_employees WHERE tr.id = :id"
    )
    void update(
        @Param("id") Long id,
        @Param("title") String title,
        @Param("description") String description,
        @Param("max_annual_income_thousands") Long maxAnnualIncomeThousands,
        @Param("max_number_employees") Long maxNumberEmployees
    );
}
