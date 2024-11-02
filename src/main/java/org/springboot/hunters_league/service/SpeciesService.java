package org.springboot.hunters_league.service;

import org.springboot.hunters_league.domain.Species;
import org.springboot.hunters_league.repository.SpeciesRepositroy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpeciesService {

    private final SpeciesRepositroy speciesRepository;

    public SpeciesService(SpeciesRepositroy speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    public Species save(Species species) {
        return speciesRepository.save(species);
    }

    public Species update(Species species) {
        return speciesRepository.save(species);
    }

    public void delete(UUID id) {
        speciesRepository.deleteById(id);
    }

    public Species findById(UUID id) {
        return speciesRepository.findById(id).orElse(null);
    }
}
