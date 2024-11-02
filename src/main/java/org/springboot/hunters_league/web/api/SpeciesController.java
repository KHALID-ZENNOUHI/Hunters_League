package org.springboot.hunters_league.web.api;

import jakarta.validation.Valid;
import org.springboot.hunters_league.domain.Species;
import org.springboot.hunters_league.service.SpeciesService;
import org.springboot.hunters_league.web.vm.mapper.SpeciesMapper;
import org.springboot.hunters_league.web.vm.requestVM.SpeciesSaveVM;
import org.springboot.hunters_league.web.vm.requestVM.SpeciesUpdateVM;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/species")
public class SpeciesController {
    private final SpeciesService speciesService;
    private final SpeciesMapper speciesMapper;

    public SpeciesController(SpeciesService speciesService, SpeciesMapper speciesMapper) {
        this.speciesService = speciesService;
        this.speciesMapper = speciesMapper;
    }

    @PostMapping
    public SpeciesSaveVM create(@Valid @RequestBody SpeciesSaveVM speciesSaveVM) {
        Species species = speciesMapper.speciesSaveVMToSpecies(speciesSaveVM);
        Species savedSpecies = speciesService.save(species);
        return speciesMapper.speciesToSpeciesSaveVM(savedSpecies);
    }

    @PutMapping
    public SpeciesUpdateVM update(@Valid @RequestBody SpeciesUpdateVM speciesUpdateVM) {
        Species species = speciesMapper.speciesUpdateVMToSpecies(speciesUpdateVM);
        Species updatedSpecies = speciesService.update(species);
        return speciesMapper.speciesToSpeciesUpdateVM(updatedSpecies);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        speciesService.delete(id);
    }


}
