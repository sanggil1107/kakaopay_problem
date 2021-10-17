package kakaopay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SoldoutResponseDto {

    private String message;

    public SoldoutResponseDto(String message) {
        this.message = message;

    }
}
