package lab.blps.main.services.choice;

import lab.blps.main.bd.entites.TaxFeatures;
import lab.blps.main.bd.entites.TaxRegimes;
import lab.blps.main.repositories.TaxRegimesRepository;
import lab.blps.main.services.entities.TaxRegimeChoice;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class ChoiceFilterByTaxFeatures extends ChoiceFilter {
    private final TaxRegimesRepository taxRegimesRepository;

    @Override
    public List<TaxRegimeWithFeaturesAndCategory> filter(TaxRegimeChoice taxRegimeChoice) {
        List<TaxFeatures> taxFeatures = taxRegimesRepository.findTaxFeatures(taxRegimeChoice.getTaxFeatures());
        List<Long> filteredTaxRegimeIds = getTaxRegimeIdsFromTaxFeatures(taxFeatures);
        List<TaxRegimes> taxRegimes = taxRegimesRepository
                .findByMaxAnnualIncomeThousandsAndMaxNumberEmployeesAndFilteredTaxRegimeIds(
                        taxRegimeChoice.getMaxAnnualIncomeThousands(),
                        taxRegimeChoice.getMaxNumberEmployees(),
                        filteredTaxRegimeIds
                );
        List<TaxRegimeWithFeaturesAndCategory> taxRegimesWithFeaturesAndCategories = new ArrayList<>();
        for (TaxRegimes taxRegime : taxRegimes) {
            List<TaxFeatures> filteredTaxFeatures = filterTaxFeatures(
                    taxRegime,
                    taxFeatures,
                    taxRegimeChoice.getTaxFeatures()
            );
            if (filteredTaxFeatures.isEmpty()) {
                continue;
            }
            TaxRegimeWithFeaturesAndCategory taxRegimeWithFeaturesAndCategory = new TaxRegimeWithFeaturesAndCategory(
                    taxRegime.getId(),
                    taxRegime.getTitle(),
                    taxRegime.getDescription(),
                    new ArrayList<>(),
                    filteredTaxFeatures,
                    taxRegime.getMaxAnnualIncomeThousands(),
                    taxRegime.getMaxNumberEmployees()
            );
            taxRegimesWithFeaturesAndCategories.add(taxRegimeWithFeaturesAndCategory);
        }
        return taxRegimesWithFeaturesAndCategories;
    }
}
