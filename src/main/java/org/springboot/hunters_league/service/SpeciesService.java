package org.springboot.hunters_league.service;

import org.springboot.hunters_league.domain.Species;
import org.springboot.hunters_league.domain.Enum.SpeciesType;
import org.springboot.hunters_league.repository.SpeciesRepositroy;
import org.springboot.hunters_league.web.error.SpeciesNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        return speciesRepository.findById(id).orElseThrow(SpeciesNotFoundException::new);
    }

    public Page<Species> filterByType(int page, int size, Optional<SpeciesType> category) {
        Pageable pageable = PageRequest.of(page, size);


        Specification<Species> spec = (root, query, cb) -> {
            if (category.isPresent()) {
                return cb.equal(root.get("category"), category.get());
            }
            return cb.conjunction();
        };

        return speciesRepository.findAll(spec, pageable);
    }
}
