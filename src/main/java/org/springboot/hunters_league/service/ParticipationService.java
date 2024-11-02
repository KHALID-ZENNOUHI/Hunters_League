package org.springboot.hunters_league.service;

import jakarta.transaction.Transactional;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final HuntService huntService;

    public ParticipationService(ParticipationRepository participationRepository, HuntService huntService) {
        this.participationRepository = participationRepository;
        this.huntService = huntService;
    }

    public void delete(UUID id) {
        participationRepository.deleteById(id);
    }


    @Transactional
    public void deleteAll(List<Participation> participations) {
        participations.forEach(huntService::deleteParticipationHunt);
        participationRepository.deleteAll(participations);
    }

    public List<Participation> findByUser(User user) {
        return participationRepository.findByUser(user);
    }
}
