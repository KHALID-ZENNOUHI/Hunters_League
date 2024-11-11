package org.springboot.hunters_league.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.web.vm.responseVM.ParticipationVM;
import org.springboot.hunters_league.web.vm.mapper.UserMapper;
import org.springboot.hunters_league.web.vm.mapper.CompetitionMapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CompetitionMapper.class})
public interface ParticipationMapper {

    // Map Participation entity to ParticipationVM
    @Mapping(source = "user", target = "user")  // Uses the UserMapper
    @Mapping(source = "competition", target = "competition")  // Uses the CompetitionMapper
    ParticipationVM participationToParticipationVM(Participation participation);

    // Map ParticipationVM to Participation entity
    @Mapping(source = "user", target = "user")  // Uses the UserMapper
    @Mapping(source = "competition", target = "competition")  // Uses the CompetitionMapper
    Participation participationVMToParticipation(ParticipationVM participationVM);
}
