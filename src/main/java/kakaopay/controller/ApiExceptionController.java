package kakaopay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import kakaopay.common.ApiErr;
import kakaopay.dto.ErrorResponseDto;
import kakaopay.dto.SoldoutResponseDto;
import kakaopay.exception.ApiException;
import kakaopay.exception.SoldoutException;

@RestControllerAdvice
public class ApiExceptionController {

    /*
        입력 값 오류
    */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponseDto> handleException(MethodArgumentTypeMismatchException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ApiErr.PARAM_MISMATCH);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponseDto> handleException(MissingServletRequestParameterException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ApiErr.PARAM_EXCEPTION);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    /*
        내부 오류
    */
	@ExceptionHandler(ApiException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponseDto> handleException(ApiException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(SoldoutException.class)
    @ResponseBody
    protected ResponseEntity<SoldoutResponseDto> handleException(SoldoutException e) {
        SoldoutResponseDto soldoutResponseDto = new SoldoutResponseDto(e.getMsg());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(soldoutResponseDto);
    }
}
