package lab.blps.security.dto.request;

import jakarta.validation.constraints.Min;
import lab.blps.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AmountRequestDto extends Dto {
    @Min(1)
    private String userId;
}
