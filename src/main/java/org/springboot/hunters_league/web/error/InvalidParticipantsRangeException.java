package org.springboot.hunters_league.web.error;

public class InvalidParticipantsRangeException extends RuntimeException {
    public InvalidParticipantsRangeException() {
        super("Minimum participants must be less than maximum participants.");
    }
}
