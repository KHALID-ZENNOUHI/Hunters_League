package org.springboot.hunters_league.service;

import jakarta.transaction.Transactional;
import org.springboot.hunters_league.domain.Hunt;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.repository.HuntRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HuntService {
    private final HuntRepository huntRepository;

    public HuntService(HuntRepository huntRepository) {
        this.huntRepository = huntRepository;
    }

    public void delete(UUID id) {
        huntRepository.deleteById(id);
    }

    public void deleteAll(List<Hunt> hunts) {
        huntRepository.deleteAll(hunts);
    }

    public List<Hunt> findHuntByParticipation(Participation participation) {
        return huntRepository.findHuntByParticipation(participation);
    }

    @Transactional
    public void deleteParticipationHunt(Participation participation) {
        deleteAll(findHuntByParticipation(participation));
    }
}
