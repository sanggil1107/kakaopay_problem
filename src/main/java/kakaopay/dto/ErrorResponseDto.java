package kakaopay.dto;

import kakaopay.common.ApiErr;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponseDto {
    private ApiErr err;

    private int code;
    private String message;

    public ErrorResponseDto(ApiErr err) {
        this.err = err;
        this.code = err.getCode();
        this.message = err.getMsg();

    }
}
