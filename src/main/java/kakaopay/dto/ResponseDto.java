package kakaopay.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseDto {
    private String code;
    private String message;
    private Object data;   

    public ResponseDto(Object object) {
        this.code = "200";
        this.message = "정상 처리";
        this.data = object;
    }
}
