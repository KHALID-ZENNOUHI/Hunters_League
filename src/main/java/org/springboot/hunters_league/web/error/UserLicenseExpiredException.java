package org.springboot.hunters_league.web.error;

public class UserLicenseExpiredException extends RuntimeException {
    public UserLicenseExpiredException() {
        super("The user's license has expired.");
    }
}
