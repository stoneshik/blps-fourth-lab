package lab.blps.security.dto.request;

import jakarta.validation.constraints.Min;
import lab.blps.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddAmountRequestDto extends Dto {
    @Min(1)
    private Long userId;
    @Min(0)
    private Integer amountRequest;
}
