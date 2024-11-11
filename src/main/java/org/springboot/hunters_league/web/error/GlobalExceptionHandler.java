package org.springboot.hunters_league.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();

            if (!errors.containsKey(fieldName)) {
                List<String> errorMessages = new ArrayList<>();
                errorMessages.add(error.getDefaultMessage());
                errors.put(fieldName, errorMessages);
            } else {
                errors.get(fieldName).add(error.getDefaultMessage());
            }
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public String handleInvalidEmailOrPasswordException(InvalidUsernameOrPasswordException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CompetitionNotFoundException.class)
    public String handleCompetitionNotFoundException(CompetitionNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExistCompetitionInTheSameWeekException.class)
    public String handleExistCompetitionInTheSameWeekException(ExistCompetitionInTheSameWeekException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidParticipantsRangeException.class)
    public String handleInvalidParticipantsRangeException(InvalidParticipantsRangeException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserLicenseExpiredException.class)
    public String handleUserLicenseExpiredException(UserLicenseExpiredException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CompetitionRegistrationClosedException.class)
    public String handleCompetitionRegistrationClosedException(CompetitionRegistrationClosedException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WeightNotApprovedException.class)
    public String handleWeightNotApprovedException(WeightNotApprovedException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParticipationNotFoundException.class)
    public String handleParticipationNotFoundException(ParticipationNotFoundException e) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSearchCriteriaException.class)
    public String handleNoSearchCriteriaException(NoSearchCriteriaException e) {
        return e.getMessage();
    }
}

