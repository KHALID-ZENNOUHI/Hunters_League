package org.springboot.hunters_league.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.web.vm.requestVM.CompetitionSaveVM;
import org.springboot.hunters_league.web.vm.requestVM.CompetitionUpdateVM;
import org.springboot.hunters_league.web.vm.responseVM.CompetitionVM;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {
    CompetitionMapper Instance = Mappers.getMapper(CompetitionMapper.class);

    CompetitionVM competitionToCompetitionVM(Competition competition);

    Competition competitionVMToCompetition(CompetitionVM competitionVM);

    CompetitionSaveVM competitionToCompetitionSaveVM(Competition competition);

    Competition competitionSaveVMToCompetition(CompetitionSaveVM competitionSaveVM);

    CompetitionUpdateVM competitionToCompetitionUpdateVM(Competition competition);

    Competition competitionUpdateVMToCompetition(CompetitionUpdateVM competitionUpdateVM);
}
