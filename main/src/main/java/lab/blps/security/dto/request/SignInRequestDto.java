package lab.blps.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import lab.blps.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignInRequestDto extends Dto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
