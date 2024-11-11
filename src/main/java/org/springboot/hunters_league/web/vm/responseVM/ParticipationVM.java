package org.springboot.hunters_league.web.vm.responseVM;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.domain.Hunt;
import org.springboot.hunters_league.domain.User;

import java.util.List;
import java.util.UUID;
@Setter
@Getter
public class ParticipationVM {
    private UUID id;

    private ProfileVM user;

    private CompetitionVM competition;

    private Double score;
}
