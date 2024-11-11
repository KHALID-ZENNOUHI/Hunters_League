package org.springboot.hunters_league.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HuntDTO {
    @NotNull
    @Positive
    private Double weight;

    @NotNull
    private UUID species_id;

    @NotNull
    private UUID participation_id;
}
