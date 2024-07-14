package lab.blps.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageResponseDto extends Dto {
    private String message;

    public MessageResponseDto(String message) {
        this.message = message;
    }
}
