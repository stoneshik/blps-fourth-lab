package lab.blps.security.dto.response;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AmountRequestResponseDto {
    @Min(1)
    private Long userId;
    @Min(0)
    private Integer amountRequest;
    public AmountRequestResponseDto(Long userId, Integer amountRequest) {
        this.userId = userId;
        this.amountRequest = amountRequest;
    }
}
