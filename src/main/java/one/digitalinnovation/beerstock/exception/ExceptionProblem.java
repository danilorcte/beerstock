package one.digitalinnovation.beerstock.exception;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionProblem {
    private Integer status;
    private LocalDateTime datetime;
    private String title;
    private List<ExceptionProblemField> fields;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ExceptionProblemField {
        String field;
        String message;

    }
}
