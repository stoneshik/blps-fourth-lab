package lab.blps.security.dto.request;

import jakarta.validation.constraints.Min;
import lab.blps.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LogoutRequestDto extends Dto {
    @Min(1)
    private Long userId;
}
