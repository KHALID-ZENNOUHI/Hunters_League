package org.springboot.hunters_league.web.vm.responseVM;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class HuntVM {
    private UUID id;

    private SpeciesVM species;

    private Double weight;

    private ParticipationVM participation;
}
