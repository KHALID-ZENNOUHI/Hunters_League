package org.springboot.hunters_league.web.error;

public class ExistCompetitionInTheSameWeekException extends RuntimeException {
    public ExistCompetitionInTheSameWeekException() {
        super("There is already a competition in the same week");
    }
}
