package org.springboot.hunters_league.web.error;

public class CompetitionRegistrationClosedException extends RuntimeException {
    public CompetitionRegistrationClosedException() {
        super("Registration is closed for this competition.");
    }
}
