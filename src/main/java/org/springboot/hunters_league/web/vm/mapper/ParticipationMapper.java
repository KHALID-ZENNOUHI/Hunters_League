package org.springboot.hunters_league.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.web.vm.responseVM.ParticipationVM;
import org.springboot.hunters_league.web.vm.mapper.UserMapper;
import org.springboot.hunters_league.web.vm.mapper.CompetitionMapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CompetitionMapper.class})
public interface ParticipationMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(source = "competition", target = "competition")
    ParticipationVM participationToParticipationVM(Participation participation);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "competition", target = "competition")
    Participation participationVMToParticipation(ParticipationVM participationVM);
}
