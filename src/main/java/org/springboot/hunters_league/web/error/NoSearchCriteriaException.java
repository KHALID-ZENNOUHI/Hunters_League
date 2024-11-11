package org.springboot.hunters_league.web.error;

public class NoSearchCriteriaException extends RuntimeException {
    public NoSearchCriteriaException() {
        super("At least one criteria must be provided to find a result");
    }
}
