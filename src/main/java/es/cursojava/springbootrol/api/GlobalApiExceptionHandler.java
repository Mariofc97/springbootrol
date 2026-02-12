package es.cursojava.springbootrol.api;

import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalApiExceptionHandler {

    @ExceptionHandler(ReglaJuegoException.class)
    public ResponseEntity<ApiError> regla(ReglaJuegoException ex, HttpServletRequest req) {

        HttpStatus status = (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("no existe"))
                ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;

        ApiError body = new ApiError(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
        ApiError body = new ApiError(500, "INTERNAL_SERVER_ERROR", ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
