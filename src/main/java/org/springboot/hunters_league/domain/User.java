package org.springboot.hunters_league.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springboot.hunters_league.domain.Enum.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Username cannot be blank.")
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role cannot be null.")
    private Role role;

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

    @PastOrPresent(message = "Join date cannot be in the future.")
    private LocalDateTime joinDate;

    @Future(message = "License expiration date must be in the future.")
    private LocalDateTime licenseExpirationDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Participation> participations;

}

