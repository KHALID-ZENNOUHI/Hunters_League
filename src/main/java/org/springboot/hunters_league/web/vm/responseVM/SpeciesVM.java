package org.springboot.hunters_league.web.vm.responseVM;

import lombok.Getter;
import lombok.Setter;
import org.springboot.hunters_league.domain.Enum.Difficulty;
import org.springboot.hunters_league.domain.Enum.SpeciesType;

import java.util.UUID;

@Getter
@Setter
public class SpeciesVM {

    private UUID id;

    private String name;

    private SpeciesType category;

    private Double minimumWeight;

    private Difficulty difficulty;

    private Integer points;
}
