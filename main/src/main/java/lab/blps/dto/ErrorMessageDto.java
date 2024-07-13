package lab.blps.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorMessageDto extends Dto {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessageDto(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }
}
