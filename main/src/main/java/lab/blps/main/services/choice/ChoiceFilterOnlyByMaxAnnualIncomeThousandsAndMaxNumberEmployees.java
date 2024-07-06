package lab.blps.main.services.choice;

import lab.blps.main.bd.entites.TaxRegimes;
import lab.blps.main.repositories.TaxRegimesRepository;
import lab.blps.main.services.entities.TaxRegimeChoice;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ChoiceFilterOnlyByMaxAnnualIncomeThousandsAndMaxNumberEmployees extends ChoiceFilter {
    private final TaxRegimesRepository taxRegimesRepository;

    @Override
    public List<TaxRegimeWithFeaturesAndCategory> filter(TaxRegimeChoice taxRegimeChoice) {
        List<TaxRegimes> taxRegimes = taxRegimesRepository.findByMaxAnnualIncomeThousandsAndMaxNumberEmployees(
                taxRegimeChoice.getMaxAnnualIncomeThousands(),
                taxRegimeChoice.getMaxNumberEmployees()
        );
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
}
