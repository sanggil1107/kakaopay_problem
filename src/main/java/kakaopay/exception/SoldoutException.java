package kakaopay.exception;

import kakaopay.common.ApiErr;
import lombok.Getter;

public class SoldoutException extends Exception {

    private ApiErr error;
    
    @Getter
    private String msg;
    public SoldoutException(ApiErr error) {
        this(error.getMsg(), null);
    }

    public SoldoutException(String msg, String message) {
        super(message);
        this.msg = msg;
    }
}
