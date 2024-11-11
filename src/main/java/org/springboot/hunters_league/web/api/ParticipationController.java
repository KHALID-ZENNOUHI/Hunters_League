package org.springboot.hunters_league.web.api;

import jakarta.validation.Valid;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.service.ParticipationService;
import org.springboot.hunters_league.service.dto.ParticipationDTO;
import org.springboot.hunters_league.web.vm.mapper.CompetitionMapper;
import org.springboot.hunters_league.web.vm.mapper.ParticipationMapper;
import org.springboot.hunters_league.web.vm.mapper.UserMapper;
import org.springboot.hunters_league.web.vm.responseVM.ParticipationVM;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/participation")
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
        ParticipationVM participationVM = participationMapper.participationToParticipationVM(savedParticipation);
        return ResponseEntity.ok(participationVM);
    }

    @GetMapping
    public ResponseEntity<Page<ParticipationVM>> findAll(@RequestParam(defaultValue = "${pagination.defaultPage}") int page, @RequestParam(defaultValue = "${pagination.defaultPageSize}") int size) {
        Page<Participation> participations = participationService.findAll(page, size);
        Page<ParticipationVM> participationVMs = participations.map(p -> {
            return participationMapper.participationToParticipationVM(p);
        });
        return ResponseEntity.ok(participationVMs);
    }

    @GetMapping("/result")
    public List<ParticipationVM> getResults(@RequestParam(required = false) UUID competition_id, @RequestParam(required = false) UUID user_id) {
        List<Participation> participations = participationService.findByCriteria(user_id, competition_id);
        List<ParticipationVM> participationVMs = participations.stream().map(participationMapper::participationToParticipationVM).toList();
        return participationVMs;
    }

    @GetMapping("/podium/{competition_id}")
    public List<ParticipationVM> getCompetitionPodium(@PathVariable UUID competition_id) {
        List<Participation> participations = participationService.competitionPodium(competition_id);
        List<ParticipationVM> participationVMs = participations.stream().map(participationMapper::participationToParticipationVM).toList();
        return participationVMs;
    }

    @GetMapping("/participation_history/{user_id}")
    public List<ParticipationVM> getParticipationHistory(@PathVariable UUID user_id) {
        List<Participation> participations = participationService.findParticipationHistory(user_id);
        List<ParticipationVM> participationVMs = participations.stream().map(participationMapper::participationToParticipationVM).toList();
        return participationVMs;
    }

}
