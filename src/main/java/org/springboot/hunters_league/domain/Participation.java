package org.springboot.hunters_league.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participation{

    @Id @GeneratedValue(strategy =  GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = "User cannot be null.")
    private User user;

    @ManyToOne
    @NotNull(message = "Competition cannot be null.")
    private Competition competition;

    @OneToMany(mappedBy = "participation")
    private List<Hunt> hunts;

    @NotNull(message = "Score cannot be null.")
    @PositiveOrZero(message = "Score must be zero or a positive value.")
    private Double score;
}