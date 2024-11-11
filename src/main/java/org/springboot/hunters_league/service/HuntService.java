package org.springboot.hunters_league.service;

import jakarta.transaction.Transactional;
import org.springboot.hunters_league.domain.Hunt;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.Species;
import org.springboot.hunters_league.repository.HuntRepository;
import org.springboot.hunters_league.service.dto.HuntDTO;
import org.springboot.hunters_league.web.error.WeightNotApprovedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HuntService {
    private final HuntRepository huntRepository;
    private final SpeciesService speciesService;
    private final ParticipationService participationService;

    public HuntService(HuntRepository huntRepository, SpeciesService speciesService, ParticipationService participationService) {
        this.huntRepository = huntRepository;
        this.speciesService = speciesService;
        this.participationService = participationService;
    }

    @Transactional
    public Hunt save(HuntDTO huntDTO) {
        Species species = speciesService.findById(huntDTO.getSpecies_id());
        Participation participation = participationService.findById(huntDTO.getParticipation_id());
        checkIfHuntWeightIsApproval(species, huntDTO.getWeight());
        Hunt hunt = Hunt.builder().species(species).participation(participation).weight(huntDTO.getWeight()).build();
        Hunt huntSaved = huntRepository.save(hunt);
        participationService.calculateScore(huntSaved.getParticipation().getId());
        return huntSaved;
    }

    public void checkIfHuntWeightIsApproval(Species species, double weight) {
        if (weight <  species.getMinimumWeight()) {
            throw new WeightNotApprovedException();
        }
    }


}
