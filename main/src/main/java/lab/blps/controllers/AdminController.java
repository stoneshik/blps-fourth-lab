package lab.blps.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.blps.dto.MessageResponseDto;
import lab.blps.exceptions.IncorrectEnumConstant;
import lab.blps.exceptions.WrongFormatUserRequestException;
import lab.blps.main.dto.TaxRegimeCreateRequestDto;
import lab.blps.main.dto.TaxRegimeDeleteRequestDto;
import lab.blps.main.dto.TaxRegimeUpdateRequestDto;
import lab.blps.main.dto.TaxRegimeWithFeaturesAndCategoryDto;
import lab.blps.main.services.CrudTaxRegimeService;
import lab.blps.main.services.KafkaService;
import lab.blps.main.services.entities.TaxRegimeCreateRequest;
import lab.blps.main.services.entities.TaxRegimeUpdateRequest;
import lab.blps.main.services.entities.TaxRegimeWithFeaturesAndCategory;
import lab.blps.main.services.entities.map.MapTaxRegimeCreateRequest;
import lab.blps.main.services.entities.map.MapTaxRegimeUpdateRequest;
import lab.blps.main.services.entities.map.MapTaxRegimeWithFeaturesAndCategory;
import lab.blps.security.dto.request.AddAmountRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final CrudTaxRegimeService crudTaxRegimeService;
    private final KafkaService kafkaService;

    @PostMapping("/amount-request/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAmountRequest(@Valid @RequestBody AddAmountRequestDto addAmountRequestDto) {
        kafkaService.sendMessage(addAmountRequestDto);
        return ResponseEntity.ok(new MessageResponseDto("Пользователю успешно добавлено количество запросов!"));
    }

    @PostMapping("/tax-regime/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTaxRegime(@Valid @RequestBody TaxRegimeCreateRequestDto taxRegimeCreateRequestDto) {
        TaxRegimeCreateRequest taxRegimeCreateRequest;
        try {
            taxRegimeCreateRequest = MapTaxRegimeCreateRequest.mapFromDto(taxRegimeCreateRequestDto);
        } catch (NullPointerException e) {
            throw new WrongFormatUserRequestException("Ошибка: Некорректный формат данных");
        } catch (IllegalArgumentException e) {
            throw new IncorrectEnumConstant("Ошибка: Передана неправильная константа");
        }
        crudTaxRegimeService.create(taxRegimeCreateRequest);
        return ResponseEntity.ok(new MessageResponseDto("Информация об налоговом режиме успешно добавлена!"));
    }

    @GetMapping("/tax-regime/read-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> readAllTaxRegime() {
        List<TaxRegimeWithFeaturesAndCategory> taxRegimes = crudTaxRegimeService.readAll();
        List<TaxRegimeWithFeaturesAndCategoryDto> taxRegimeDtoList = new ArrayList<>();
        for (TaxRegimeWithFeaturesAndCategory taxRegime : taxRegimes) {
            taxRegimeDtoList.add(MapTaxRegimeWithFeaturesAndCategory.mapToDto(taxRegime));
        }
        return new ResponseEntity<>(taxRegimeDtoList, HttpStatus.OK);
    }

    @PostMapping("/tax-regime/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTaxRegime(@Valid @RequestBody TaxRegimeUpdateRequestDto taxRegimeUpdateRequestDto) {
        TaxRegimeUpdateRequest taxRegimeUpdateRequest;
        try {
            taxRegimeUpdateRequest = MapTaxRegimeUpdateRequest.mapFromDto(taxRegimeUpdateRequestDto);
        } catch (NullPointerException e) {
            throw new WrongFormatUserRequestException("Ошибка: Некорректный формат данных");
        } catch (IllegalArgumentException e) {
            throw new IncorrectEnumConstant("Ошибка: Передана неправильная константа");
        }
        checkTaxRegimeId(taxRegimeUpdateRequest.getId());
        crudTaxRegimeService.update(taxRegimeUpdateRequest);
        return ResponseEntity.ok(new MessageResponseDto("Информация об налоговом режиме успешно обновлена!"));
    }

    @DeleteMapping("/tax-regime/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTaxRegime(@Valid @RequestBody TaxRegimeDeleteRequestDto taxRegimeDeleteRequestDto) {
        checkTaxRegimeId(taxRegimeDeleteRequestDto.getId());
        crudTaxRegimeService.delete(taxRegimeDeleteRequestDto.getId());
        return ResponseEntity.ok(new MessageResponseDto("Информация об налоговом режиме успешно удалена!"));
    }

    private void checkTaxRegimeId(Long id) {
        if (crudTaxRegimeService.getById(id) == null) {
            throw new EntityNotFoundException(
                String.format("Ошибка: Налоговй режим id %s не найден", id)
            );
        }
    }
}
