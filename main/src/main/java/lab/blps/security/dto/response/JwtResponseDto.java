package lab.blps.security.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import lab.blps.dto.Dto;

@EqualsAndHashCode(callSuper = true)
@Data
public class JwtResponseDto extends Dto {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private List<String> roles;

    public JwtResponseDto(String accessToken, String refreshToken, Long id, String username, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
