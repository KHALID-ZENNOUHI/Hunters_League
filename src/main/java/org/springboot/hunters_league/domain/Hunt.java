package org.springboot.hunters_league.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hunt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = "Species cannot be null.")
    private Species species;

    @NotNull(message = "Weight cannot be null.")
    @Positive(message = "Weight must be a positive value.")
    private Double weight;

    @ManyToOne
    @NotNull(message = "Participation cannot be null.")
    private Participation participation;


}