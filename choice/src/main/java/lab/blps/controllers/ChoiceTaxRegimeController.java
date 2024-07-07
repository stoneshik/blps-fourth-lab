package lab.blps.controllers;

import jakarta.validation.Valid;
import lab.blps.exceptions.IncorrectEnumConstant;
import lab.blps.exceptions.WrongFormatUserRequestException;
import lab.blps.main.dto.TaxRegimeChoiceDto;
import lab.blps.main.dto.TaxRegimeWithFeaturesAndCategoryDto;
import lab.blps.main.services.ChoiceTaxRegimeService;
import lab.blps.main.services.entities.TaxRegimeChoice;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lab.blps.main.services.entities.map.MapTaxRegimeChoice;
import lab.blps.main.services.entities.map.MapTaxRegimeWithFeaturesAndCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChoiceTaxRegimeController {
    private final ChoiceTaxRegimeService choiceTaxRegimeService;

    @Transactional
    @GetMapping(
            path = "/api/choice-tax-regime",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> choiceTaxRegime(@Valid @RequestBody TaxRegimeChoiceDto taxRegimeChoiceDto) {
        TaxRegimeChoice taxRegimeChoice;
        try {
            taxRegimeChoice = MapTaxRegimeChoice.mapFromDto(taxRegimeChoiceDto);
        } catch (NullPointerException e) {
            throw new WrongFormatUserRequestException("Ошибка: Некорректный формат данных");
        } catch (IllegalArgumentException e) {
            throw new IncorrectEnumConstant("Ошибка: Передана неправильная константа");
        }
        List<TaxRegimeWithFeaturesAndCategory> taxRegimes = choiceTaxRegimeService.choice(taxRegimeChoice);
        List<TaxRegimeWithFeaturesAndCategoryDto> taxRegimeDtoList = new ArrayList<>();
        for (TaxRegimeWithFeaturesAndCategory taxRegime : taxRegimes) {
            taxRegimeDtoList.add(MapTaxRegimeWithFeaturesAndCategory.mapToDto(taxRegime));
        }
        return new ResponseEntity<>(taxRegimeDtoList, HttpStatus.OK);
    }
}
