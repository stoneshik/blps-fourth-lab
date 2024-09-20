package lab.blps.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AddAmountRequestDto extends Dto {
    private String userId;
    @Min(0)
    private Integer amountRequest;
}
