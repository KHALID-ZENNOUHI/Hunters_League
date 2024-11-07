package org.springboot.hunters_league.web.vm.requestVM;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springboot.hunters_league.domain.Enum.Role;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class UserVM {

    private UUID id;

    @NotBlank
    private String username;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Column(unique = true)
    private String cin;

    @NotBlank
    @Email
    private String email;

    private String nationality;

    @PastOrPresent(message = "Join date cannot be in the future.")
    private LocalDateTime joinDate;

    @Future
    private LocalDateTime licenseExpirationDate;
}
