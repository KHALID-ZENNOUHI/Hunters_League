package org.springboot.hunters_league.web.error;

public class WeightNotApprovedException extends RuntimeException {
    public WeightNotApprovedException() {
        super("Weight is not approved for this hunt");
    }
}
