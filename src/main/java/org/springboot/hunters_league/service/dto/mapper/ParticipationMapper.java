package org.springboot.hunters_league.service.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.web.vm.responseVM.ParticipationVM;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {
    ParticipationMapper INSTANCE = Mappers.getMapper(ParticipationMapper.class);

    ParticipationVM participationToParticipationVM(Participation participation);
}
