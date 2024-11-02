package org.springboot.hunters_league.web.vm.requestVM;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterVM {
    @NotBlank(message = "Username cannot be blank.")
    private String username;

    @Size(min = 8, message = "The password must be at least 8 characters long.")
    @Pattern(regexp = ".*[A-Z].*", message = "The password must contain at least one uppercase letter.")
    @Pattern(regexp = ".*[a-z].*", message = "The password must contain at least one lowercase letter.")
    @Pattern(regexp = ".*\\d.*", message = "The password must contain at least one digit.")
    private String password;

    @NotBlank(message = "First name cannot be blank.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    private String lastName;

    @NotBlank(message = "CIN cannot be blank.")
    @Column(unique = true)
    private String cin;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "Email should be valid.")
    private String email;

    private String nationality;
}
