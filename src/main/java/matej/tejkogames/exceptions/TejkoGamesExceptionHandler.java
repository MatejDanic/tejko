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
import matej.tejkogames.models.general.enums.MessageType;
import matej.tejkogames.models.general.payload.responses.MessageResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class TejkoGamesExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    ApiErrorServiceImpl apiErrorService;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<MessageResponse> handleException(RuntimeException exception, WebRequest request) {
        ApiError apiError = new ApiError(exception);            
        apiErrorService.save(apiError);
        return new ResponseEntity<>(new MessageResponse("Error", MessageType.ERROR, apiError.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}