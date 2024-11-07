package org.springboot.hunters_league.web.api;

import jakarta.validation.Valid;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.service.ParticipationService;
import org.springboot.hunters_league.service.dto.ParticipationDTO;
import org.springboot.hunters_league.service.dto.mapper.ParticipationMapper;
import org.springboot.hunters_league.web.vm.mapper.CompetitionMapper;
import org.springboot.hunters_league.web.vm.mapper.UserMapper;
import org.springboot.hunters_league.web.vm.responseVM.CompetitionVM;
import org.springboot.hunters_league.web.vm.responseVM.ParticipationVM;
import org.springboot.hunters_league.web.vm.responseVM.ProfileVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/participations")
public class ParticipationController {
    ParticipationService participationService;
    ParticipationMapper participationMapper;
    UserMapper userMapper;
    CompetitionMapper competitionMapper;

    public ParticipationController(ParticipationService participationService, ParticipationMapper participationMapper, UserMapper userMapper, CompetitionMapper competitionMapper) {
        this.participationService = participationService;
        this.participationMapper = participationMapper;
        this.userMapper = userMapper;
        this.competitionMapper = competitionMapper;
    }

    @PostMapping
    public ResponseEntity<ParticipationVM> inscription(@Valid @RequestBody ParticipationDTO participationDTO) {
        Participation savedParticipation = participationService.inscription(participationDTO);
        ProfileVM user = userMapper.userToProfileVM(savedParticipation.getUser());
        CompetitionVM competition = competitionMapper.competitionToCompetitionVM(savedParticipation.getCompetition());
        ParticipationVM participationVM = new ParticipationVM();
        participationVM.setId(savedParticipation.getId());
        participationVM.setUser(user);
        participationVM.setCompetition(competition);
        return ResponseEntity.ok(participationVM);
    }
}
