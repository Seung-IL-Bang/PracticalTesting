package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // @ControllerAdvice 와 혼동 주의
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 해당 애너테이션 없을 경우 응답 상태 OK 로 날라감.
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e) {

        return ApiResponse.of(HttpStatus.BAD_REQUEST,
                e.getAllErrors().get(0).getDefaultMessage(),
                null);

    }
}
