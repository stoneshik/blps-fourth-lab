package lab.blps.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lab.blps.dto.Dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignUpRequestDto extends Dto {
    @NotBlank
    private String login;
    private Set<String> role;
    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
}
