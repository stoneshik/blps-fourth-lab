package lab.blps.main.services.choice;

import lab.blps.main.bd.entites.TaxFeatures;
import lab.blps.main.bd.entites.TaxRegimes;
import lab.blps.main.bd.entites.TaxpayerCategories;
import lab.blps.main.bd.entites.enums.TaxFeatureEnum;
import lab.blps.main.bd.entites.enums.TaxpayerCategoryEnum;
import lab.blps.main.services.entities.TaxRegimeChoice;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class ChoiceFilter {
    public abstract List<TaxRegimeWithFeaturesAndCategory> filter(TaxRegimeChoice taxRegimeChoice);

    protected List<Long> getTaxRegimeIdsFromTaxpayerCategories(List<TaxpayerCategories> taxpayerCategories) {
        return taxpayerCategories
                .stream()
                .map(taxpayerCategory -> taxpayerCategory.getTaxRegimes().getId())
                .toList();
    }

    protected List<Long> getTaxRegimeIdsFromTaxFeatures(List<TaxFeatures> taxFeatures) {
        return taxFeatures
                .stream()
                .map(taxFeature -> taxFeature.getTaxRegimes().getId())
                .toList();
    }

    protected List<TaxpayerCategories> filterTaxpayerCategory(
            TaxRegimes taxRegimes,
            List<TaxpayerCategories> taxpayerCategories,
            List<TaxpayerCategoryEnum> choiceTaxpayerCategoryEnums
    ) {
        List<TaxpayerCategories> filteredTaxpayerCategories = taxpayerCategories
                .stream()
                .filter(taxpayerCategory -> taxpayerCategory.getTaxRegimes().getId().equals(taxRegimes.getId()))
                .toList();
        if (filteredTaxpayerCategories.size() != choiceTaxpayerCategoryEnums.size()) {
            return new ArrayList<>();
        }
        List<TaxpayerCategoryEnum> filteredTaxpayerCategoryEnums = filteredTaxpayerCategories
                .stream()
                .map(TaxpayerCategories::getTaxpayerCategoryEnum)
                .toList();
        if (!new HashSet<>(choiceTaxpayerCategoryEnums).containsAll(filteredTaxpayerCategoryEnums)) {
            return new ArrayList<>();
        }
        return filteredTaxpayerCategories;
    }

    protected List<TaxFeatures> filterTaxFeatures(
            TaxRegimes taxRegimes,
            List<TaxFeatures> taxFeatures,
            List<TaxFeatureEnum> choiceTaxFeaturesEnums
    ) {
        List<TaxFeatures> filteredTaxFeatures = taxFeatures
                .stream()
                .filter(taxFeature -> taxFeature.getTaxRegimes().getId().equals(taxRegimes.getId()))
                .toList();
        if (filteredTaxFeatures.size() != choiceTaxFeaturesEnums.size()) {
            return new ArrayList<>();
        }
        List<TaxFeatureEnum> filteredTaxFeaturesEnum = filteredTaxFeatures
                .stream()
                .map(TaxFeatures::getTaxFeatureEnum)
                .toList();
        if (!new HashSet<>(choiceTaxFeaturesEnums).containsAll(filteredTaxFeaturesEnum)) {
            return new ArrayList<>();
        }
        return filteredTaxFeatures;
    }
}
