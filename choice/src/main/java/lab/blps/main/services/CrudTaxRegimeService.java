package lab.blps.main.services;

import lab.blps.main.bd.entites.TaxFeatures;
import lab.blps.main.bd.entites.TaxRegimes;
import lab.blps.main.bd.entites.TaxpayerCategories;
import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.repositories.TaxFeaturesRepository;
import lab.blps.main.repositories.TaxRegimesRepository;
import lab.blps.main.repositories.TaxpayerCategoriesRepository;
import lab.blps.main.services.entities.TaxRegimeCreateRequest;
import lab.blps.main.services.entities.TaxRegimeUpdateRequest;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrudTaxRegimeService {
    private final TaxRegimesRepository taxRegimesRepository;
    private final TaxFeaturesRepository taxFeaturesRepository;
    private final TaxpayerCategoriesRepository taxpayerCategoriesRepository;

    public TaxRegimes getById(Long id) {
        return taxRegimesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void create(TaxRegimeCreateRequest taxRegimeCreateRequest) {
        TaxRegimes taxRegimes = new TaxRegimes();
        taxRegimes.setTitle(taxRegimeCreateRequest.getTitle());
        taxRegimes.setDescription(taxRegimeCreateRequest.getDescription());
        taxRegimes.setMaxAnnualIncomeThousands(taxRegimeCreateRequest.getMaxAnnualIncomeThousands());
        taxRegimes.setMaxNumberEmployees(taxRegimeCreateRequest.getMaxNumberEmployees());
        TaxRegimes newTaxRegime = taxRegimesRepository.saveAndFlush(taxRegimes);
        saveTaxFeatures(newTaxRegime, taxRegimeCreateRequest.getTaxFeatures());
        saveTaxpayerCategories(newTaxRegime, taxRegimeCreateRequest.getTaxpayerCategories());
    }

    public List<TaxRegimeWithFeaturesAndCategory> readAll() {
        List<TaxRegimes> taxRegimes = taxRegimesRepository.findAll();
        List<TaxRegimeWithFeaturesAndCategory> taxRegimesWithFeaturesAndCategories = new ArrayList<>();
        for (TaxRegimes taxRegime : taxRegimes) {
            TaxRegimeWithFeaturesAndCategory taxRegimeWithFeaturesAndCategory = new TaxRegimeWithFeaturesAndCategory(
                taxRegime.getId(),
                taxRegime.getTitle(),
                taxRegime.getDescription(),
                new ArrayList<>(),
                new ArrayList<>(),
                taxRegime.getMaxAnnualIncomeThousands(),
                taxRegime.getMaxNumberEmployees()
            );
            taxRegimesWithFeaturesAndCategories.add(taxRegimeWithFeaturesAndCategory);
        }
        return taxRegimesWithFeaturesAndCategories;
    }

    @Transactional
    public void update(TaxRegimeUpdateRequest taxRegimeUpdateRequest) {
        TaxRegimes taxRegimes = new TaxRegimes();
        taxRegimes.setId(taxRegimeUpdateRequest.getId());
        taxRegimes.setTitle(taxRegimeUpdateRequest.getTitle());
        taxRegimes.setDescription(taxRegimeUpdateRequest.getDescription());
        taxRegimes.setMaxAnnualIncomeThousands(taxRegimeUpdateRequest.getMaxAnnualIncomeThousands());
        taxRegimes.setMaxNumberEmployees(taxRegimeUpdateRequest.getMaxNumberEmployees());
        taxRegimesRepository.update(
            taxRegimes.getId(),
            taxRegimes.getTitle(),
            taxRegimes.getDescription(),
            taxRegimes.getMaxAnnualIncomeThousands(),
            taxRegimes.getMaxNumberEmployees()
        );
        for (TaxFeatureEnum taxFeatureEnum : taxRegimeUpdateRequest.getTaxFeatures()) {
            taxFeaturesRepository.update(taxRegimes.getId(), taxFeatureEnum);
        }
        for (TaxpayerCategoryEnum taxpayerCategoryEnum : taxRegimeUpdateRequest.getTaxpayerCategories()) {
            taxpayerCategoriesRepository.update(taxRegimes.getId(), taxpayerCategoryEnum);
        }
    }

    public void delete(Long taxRegimeId) {
        List<Long> ids = new ArrayList<>();
        ids.add(taxRegimeId);
        taxRegimesRepository.deleteAllByIdInBatch(ids);
    }

    private void saveTaxFeatures(TaxRegimes newTaxRegime, List<TaxFeatureEnum> taxFeaturesEnum) {
        for (TaxFeatureEnum taxFeatureEnum : taxFeaturesEnum) {
            TaxFeatures taxFeatures = new TaxFeatures();
            taxFeatures.setTaxRegimes(newTaxRegime);
            taxFeatures.setTaxFeatureEnum(taxFeatureEnum);
            taxFeaturesRepository.saveAndFlush(taxFeatures);
        }
    }

    private void saveTaxpayerCategories(TaxRegimes newTaxRegime, List<TaxpayerCategoryEnum> taxpayerCategoryEnums) {
        for (TaxpayerCategoryEnum taxpayerCategoryEnum : taxpayerCategoryEnums) {
            TaxpayerCategories taxpayerCategories = new TaxpayerCategories();
            taxpayerCategories.setTaxRegimes(newTaxRegime);
            taxpayerCategories.setTaxpayerCategoryEnum(taxpayerCategoryEnum);
            taxpayerCategoriesRepository.saveAndFlush(taxpayerCategories);
        }
    }
}
