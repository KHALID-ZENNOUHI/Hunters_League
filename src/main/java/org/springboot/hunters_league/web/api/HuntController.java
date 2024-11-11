package org.springboot.hunters_league.web.api;

import jakarta.validation.Valid;
import org.springboot.hunters_league.domain.Hunt;
import org.springboot.hunters_league.service.HuntService;
import org.springboot.hunters_league.service.dto.HuntDTO;
import org.springboot.hunters_league.web.vm.mapper.ParticipationMapper;
import org.springboot.hunters_league.web.vm.mapper.SpeciesMapper;
import org.springboot.hunters_league.web.vm.responseVM.HuntVM;
import org.springboot.hunters_league.web.vm.responseVM.ParticipationVM;
import org.springboot.hunters_league.web.vm.responseVM.SpeciesVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hunt")
public class HuntController {
    private final HuntService huntService;
    private final SpeciesMapper speciesMapper;
    private final ParticipationMapper participationMapper;

    public HuntController(HuntService huntService, SpeciesMapper speciesMapper, ParticipationMapper participationMapper) {
        this.huntService = huntService;
        this.speciesMapper = speciesMapper;
        this.participationMapper = participationMapper;
    }

    @PostMapping
    public ResponseEntity<HuntVM> save(@Valid @RequestBody HuntDTO huntDTO) {
        Hunt savedHunt = huntService.save(huntDTO);
        HuntVM huntVM = new HuntVM();
        huntVM.setId(savedHunt.getId());
        huntVM.setWeight(savedHunt.getWeight());
        SpeciesVM speciesVM = speciesMapper.speciesToSpeciesVM(savedHunt.getSpecies());
        huntVM.setSpecies(speciesVM);
        ParticipationVM participationVM = participationMapper.participationToParticipationVM(savedHunt.getParticipation());
        huntVM.setParticipation(participationVM);
        return ResponseEntity.ok(huntVM);
    }
}
