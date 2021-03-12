package one.digitalinnovation.beerstock.exception;

import one.digitalinnovation.beerstock.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BeerAlreadyRegisteredException.class)
    public ResponseEntity<Object> handleBeerAlreadyResteredExcpetion(BeerAlreadyRegisteredException ex, WebRequest request){
        var status = HttpStatus.BAD_REQUEST;

        ExceptionProblem exceptionProblem = new ExceptionProblem();
        exceptionProblem.setStatus(status.value());
        exceptionProblem.setTitle(ex.getMessage());
        exceptionProblem.setDatetime(LocalDateTime.now());

        return handleExceptionInternal(ex, exceptionProblem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionProblem exceptionProblem = new ExceptionProblem();
        var fields = new ArrayList<ExceptionProblem.ExceptionProblemField>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()){
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            fields.add(new ExceptionProblem.ExceptionProblemField(name ,message));
        }
        exceptionProblem.setStatus(status.value());
        exceptionProblem.setTitle("Um ou mais campos estão inválidos. "
                                + "Faça o preenchimento correto e tente novamente! ");

        exceptionProblem.setDatetime(LocalDateTime.now());

        exceptionProblem.setFields(fields);

        return super.handleExceptionInternal(ex, exceptionProblem, headers, status, request);
    }
}
