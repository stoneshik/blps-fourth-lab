package lab.blps.main.services.choice;

import lab.blps.main.bd.entites.TaxRegimes;
import lab.blps.main.bd.entites.TaxpayerCategories;
import lab.blps.main.repositories.TaxRegimesRepository;
import lab.blps.main.services.entities.TaxRegimeChoice;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class ChoiceFilterByTaxpayerCategory extends ChoiceFilter {
    private final TaxRegimesRepository taxRegimesRepository;

    @Override
    public List<TaxRegimeWithFeaturesAndCategory> filter(TaxRegimeChoice taxRegimeChoice) {
        List<TaxpayerCategories> taxpayerCategories = taxRegimesRepository.findTaxpayerCategories(
                taxRegimeChoice.getTaxpayerCategories()
        );
        List<Long> filteredTaxRegimeIds = getTaxRegimeIdsFromTaxpayerCategories(taxpayerCategories);
        List<TaxRegimes> taxRegimes = taxRegimesRepository
                .findByMaxAnnualIncomeThousandsAndMaxNumberEmployeesAndFilteredTaxRegimeIds(
                        taxRegimeChoice.getMaxAnnualIncomeThousands(),
                        taxRegimeChoice.getMaxNumberEmployees(),
                        filteredTaxRegimeIds
                );
        List<TaxRegimeWithFeaturesAndCategory> taxRegimesWithFeaturesAndCategories = new ArrayList<>();
        for (TaxRegimes taxRegime : taxRegimes) {
            List<TaxpayerCategories> filteredTaxpayerCategories = filterTaxpayerCategory(
                    taxRegime,
                    taxpayerCategories,
                    taxRegimeChoice.getTaxpayerCategories()
            );
            if (filteredTaxpayerCategories.isEmpty()) {
                continue;
            }
            TaxRegimeWithFeaturesAndCategory taxRegimeWithFeaturesAndCategory = new TaxRegimeWithFeaturesAndCategory(
                    taxRegime.getId(),
                    taxRegime.getTitle(),
                    taxRegime.getDescription(),
                    filteredTaxpayerCategories,
                    new ArrayList<>(),
                    taxRegime.getMaxAnnualIncomeThousands(),
                    taxRegime.getMaxNumberEmployees()
            );
            taxRegimesWithFeaturesAndCategories.add(taxRegimeWithFeaturesAndCategory);
        }
        return taxRegimesWithFeaturesAndCategories;
    }
}
