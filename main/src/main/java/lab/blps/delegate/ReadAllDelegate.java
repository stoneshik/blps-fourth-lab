package lab.blps.delegate;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import lab.blps.main.dto.TaxRegimeWithFeaturesAndCategoryDto;
import lab.blps.main.services.CrudTaxRegimeService;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lab.blps.main.services.entities.map.MapTaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReadAllDelegate implements JavaDelegate {
    private final CrudTaxRegimeService crudTaxRegimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<TaxRegimeWithFeaturesAndCategory> taxRegimes = crudTaxRegimeService.readAll();
        List<TaxRegimeWithFeaturesAndCategoryDto> taxRegimeDtoList = new ArrayList<>();
        for (TaxRegimeWithFeaturesAndCategory taxRegime : taxRegimes) {
            taxRegimeDtoList.add(MapTaxRegimeWithFeaturesAndCategory.mapToDto(taxRegime));
        }
        delegateExecution.setVariable("response", taxRegimeDtoList);
    }
}
