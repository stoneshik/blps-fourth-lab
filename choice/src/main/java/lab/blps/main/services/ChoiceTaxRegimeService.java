package lab.blps.main.services;

import lab.blps.main.repositories.TaxRegimesRepository;
import lab.blps.main.services.choice.*;
import lab.blps.main.services.entities.TaxRegimeChoice;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceTaxRegimeService {
    private final TaxRegimesRepository taxRegimesRepository;

    public List<TaxRegimeWithFeaturesAndCategory> choice(TaxRegimeChoice taxRegimeChoice) {
        ChoiceFilter chosenFilter = chooseFilter(taxRegimeChoice);
        return chosenFilter.filter(taxRegimeChoice);
    }

    private ChoiceFilter chooseFilter(TaxRegimeChoice taxRegimeChoice) {
        ChoiceFilter chosenFilter;
        if (taxRegimeChoice.getTaxpayerCategories().isEmpty()) {
            if (taxRegimeChoice.getTaxFeatures().isEmpty()) {
                chosenFilter = new ChoiceFilterOnlyByMaxAnnualIncomeThousandsAndMaxNumberEmployees(taxRegimesRepository);
            } else {
                chosenFilter = new ChoiceFilterByTaxFeatures(taxRegimesRepository);
            }
        } else {
            if (taxRegimeChoice.getTaxFeatures().isEmpty()) {
                chosenFilter = new ChoiceFilterByTaxpayerCategory(taxRegimesRepository);
            } else {
                chosenFilter = new ChoiceFilterByTaxpayerCategoryAndTaxFeature(taxRegimesRepository);
            }
        }
        return chosenFilter;
    }
}
