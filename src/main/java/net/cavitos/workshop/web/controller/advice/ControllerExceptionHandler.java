package net.cavitos.workshop.web.controller.advice;

import net.cavitos.workshop.domain.exception.AuthenticationException;
import net.cavitos.workshop.domain.exception.BusinessException;
import net.cavitos.workshop.domain.exception.ValidationException;
import net.cavitos.workshop.domain.model.error.FieldError;
import net.cavitos.workshop.domain.model.web.response.error.ErrorResponse;
import net.cavitos.workshop.domain.model.web.response.error.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);    

    @ExceptionHandler
    public ResponseEntity<Object> handleFallBackException(Exception exception, WebRequest request) {

        LOGGER.error("unable to process request - ", exception);

        var error = buildErrorResponse("Unable to process request / service unavailable");
        return handleExceptionInternal(exception, error, buildHttpHeaders(), 
            HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException exception, WebRequest request) {

        LOGGER.error("unable to process request - ", exception);

        var error = buildErrorResponse(exception.getMessage());
        
        return handleExceptionInternal(exception, error, buildHttpHeaders(),
            exception.getHttpStatus(), request);        
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleRequestValidationException(ValidationException exception, WebRequest request) {

        LOGGER.error("unable to process request because a validation exception - ", exception);

        var error = buildValidationErrorResponse(exception.getFieldErrors());
        return handleExceptionInternal(exception, error, buildHttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception, WebRequest request) {

        LOGGER.error("access denied to resource", exception);

        var error = buildErrorResponse(exception.getMessage());
        return handleExceptionInternal(exception, error, buildHttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        LOGGER.error("validation failure - ", exception);

        var error = new ValidationErrorResponse();
        error.setMessage("Request validation failed");

        var errors = exception.getAllValidationResults()
                .stream()
                .flatMap(result -> {

                    final var fieldValue = result.getArgument();

                    return result.getResolvableErrors()
                            .stream()
                            .map(resolvable -> {

                                final var fieldError = new FieldError();

                                var names = resolvable.getCodes();
                                if (nonNull(names) && names.length >= 2) {

                                    fieldError.setFieldName(names[1]);
                                }

                                fieldError.setValue(nonNull(fieldValue) ? fieldValue.toString() : "");
                                fieldError.setError(resolvable.getDefaultMessage());

                                return fieldError;
                            });
                }).toList();

        error.setErrors(errors);

        return handleExceptionInternal(exception, error, buildHttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // ------------------------------------------------------------------------------------------------

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        LOGGER.error("unable to process request because a validation exception - ", exception);

        final var error = new ValidationErrorResponse();
        error.setMessage("Request validation failed");

        var errors = exception.getFieldErrors()
                .stream()
                .map(fieldError -> {

                    var fError = new FieldError();

                    fError.setFieldName(fieldError.getField());

                    if (nonNull(fieldError.getRejectedValue())) {

                        fError.setValue(fieldError.getRejectedValue().toString());
                    }

                    fError.setError(fieldError.getDefaultMessage());

                    return fError;
                }).toList();

        error.setErrors(errors);

        return handleExceptionInternal(exception, error, buildHttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // ------------------------------------------------------------------------------------------------

    private ErrorResponse buildErrorResponse(String message) {

        var error = new ErrorResponse();
        error.setMessage(message);

        return error;
    }

    private ValidationErrorResponse buildValidationErrorResponse(List<FieldError> errors) {

        final var error = new ValidationErrorResponse();
        error.setErrors(errors);
        error.setMessage("Request validation failed");

        return error;
    }

    private HttpHeaders buildHttpHeaders() {

        return new HttpHeaders();
    }    
}
