package matej.tejkogames.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import matej.tejkogames.api.services.ApiErrorServiceImpl;
import matej.tejkogames.models.general.ApiError;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class TejkoGamesExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ApiErrorServiceImpl apiErrorService;

    @ExceptionHandler(value = { IllegalMoveException.class, UsernameTakenException.class, InvalidOwnershipException.class, RuntimeException.class })
    protected ResponseEntity<Object> handleException(RuntimeException exception, WebRequest request) {
        ApiError apiError = new ApiError(exception);
        apiErrorService.save(apiError);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}