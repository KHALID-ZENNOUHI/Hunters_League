package org.springboot.hunters_league.web.vm.responseVM;

import lombok.Getter;
import lombok.Setter;
import org.springboot.hunters_league.domain.Enum.SpeciesType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CompetitionVM {
    private UUID id;

    private String code;

    private String location;

    private LocalDateTime date;

    private SpeciesType speciesType;

    private Boolean openRegistration;
}
