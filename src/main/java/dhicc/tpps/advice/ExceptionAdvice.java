package dhicc.tpps.advice;

import dhicc.tpps.exception.DhiccException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(DhiccException.class)
    public ResponseEntity<ErrorResponse> DhiccException(DhiccException e) {
        int status = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(status)
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(status).body(body);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        log.error(" === NOT_FOUND EXCEPTION ===", e);

        int status = HttpStatus.NOT_FOUND.value();

        ErrorResponse body = ErrorResponse.builder()
                .code(status)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error(" === INTERNAL_SERVER_ERROR ===", e);

        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        ErrorResponse body = ErrorResponse.builder()
                .code(status)
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(status).body(body);
    }

    //TODO:: Add more exception handler
}
