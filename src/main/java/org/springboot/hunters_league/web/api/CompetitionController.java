package org.springboot.hunters_league.web.api;

import jakarta.validation.Valid;
import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.repository.dto.CompetitionDTO;
import org.springboot.hunters_league.service.CompetitionService;
import org.springboot.hunters_league.web.vm.mapper.CompetitionMapper;
import org.springboot.hunters_league.web.vm.requestVM.CompetitionSaveVM;
import org.springboot.hunters_league.web.vm.requestVM.CompetitionUpdateVM;
import org.springboot.hunters_league.web.vm.responseVM.CompetitionVM;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/competitions")
public class CompetitionController {
    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;

    public CompetitionController(CompetitionService competitionService, CompetitionMapper competitionMapper) {
        this.competitionService = competitionService;
        this.competitionMapper = competitionMapper;
    }

    @PostMapping
    public ResponseEntity<CompetitionVM> create(@Valid @RequestBody CompetitionSaveVM competitionSaveVM) {
        Competition competition = competitionMapper.competitionSaveVMToCompetition(competitionSaveVM);
        Competition savedCompetition = competitionService.save(competition);
        CompetitionVM savedCompetitionVM = competitionMapper.competitionToCompetitionVM(savedCompetition);
        return ResponseEntity.ok(savedCompetitionVM);
    }

    @PutMapping
    public ResponseEntity<CompetitionVM> update(@Valid @RequestBody CompetitionUpdateVM CompetitionUpdateVM) {
        Competition competition = competitionMapper.competitionUpdateVMToCompetition(CompetitionUpdateVM);
        Competition updatedCompetition = competitionService.update(competition);
        CompetitionVM competitionVM = competitionMapper.competitionToCompetitionVM(updatedCompetition);
        return ResponseEntity.ok(competitionVM);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        competitionService.delete(id);
        return ResponseEntity.ok("Competition deleted successfully");
    }

    @GetMapping
    public ResponseEntity<Page<CompetitionVM>> findAll(@RequestParam(name = "page", required = false, defaultValue = "${pagination.defaultPage}") int page, @RequestParam(name = "size", required = false, defaultValue = "${pagination.defaultPageSize}") int size) {
        Page<Competition> competitions = competitionService.findAll(page, size);
        Page<CompetitionVM> competitionVMs = competitions.map(competitionMapper::competitionToCompetitionVM);
        return ResponseEntity.ok(competitionVMs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionDTO> competitionDetails(@PathVariable UUID id) {
        CompetitionDTO competition = competitionService.competitionDetails(id);
        return ResponseEntity.ok(competition);
    }
}
