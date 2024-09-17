package lab.blps.delegate;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.identity.Authentication;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lab.blps.main.dto.TaxRegimeWithFeaturesAndCategoryDto;
import lab.blps.main.services.CrudTaxRegimeService;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lab.blps.main.services.entities.map.MapTaxRegimeWithFeaturesAndCategory;
import lab.blps.security.bd.entities.user.RoleEnum;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReadAllDelegate implements JavaDelegate {
    private final CrudTaxRegimeService crudTaxRegimeService;
    private final ObjectMapper objectMapper;
    private final IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Authentication authentication = identityService.getCurrentAuthentication();
        if (!authentication.getGroupIds().contains(RoleEnum.ADMIN.name())) {
            throw new BpmnError("unauthorized");
        }
        List<TaxRegimeWithFeaturesAndCategory> taxRegimes = crudTaxRegimeService.readAll();
        List<TaxRegimeWithFeaturesAndCategoryDto> taxRegimeDtoList = new ArrayList<>();
        for (TaxRegimeWithFeaturesAndCategory taxRegime : taxRegimes) {
            taxRegimeDtoList.add(MapTaxRegimeWithFeaturesAndCategory.mapToDto(taxRegime));
        }
        String responseString = objectMapper.writeValueAsString(taxRegimeDtoList);
        delegateExecution.setVariable("response", responseString);
    }
}
