package org.springboot.hunters_league.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ParticipationDTO {
    @NotNull
    private UUID user_id;

    @NotNull
    private UUID competition_id;
}
