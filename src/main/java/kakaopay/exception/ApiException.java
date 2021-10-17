package kakaopay.exception;

import kakaopay.common.ApiErr;
import lombok.Getter;

public class ApiException extends Exception {
    @Getter
    private ApiErr error;

    public ApiException(ApiErr error) {
        this(error, null);
    }

    public ApiException(ApiErr error, String message) {
        super(message);
        this.error = error;
    }
}
