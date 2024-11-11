package org.springboot.hunters_league.service;

import jakarta.persistence.criteria.Predicate;
import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.repository.ParticipationRepository;
import org.springboot.hunters_league.service.dto.ParticipationDTO;
import org.springboot.hunters_league.web.error.CompetitionRegistrationClosedException;
import org.springboot.hunters_league.web.error.NoSearchCriteriaException;
import org.springboot.hunters_league.web.error.ParticipationNotFoundException;
import org.springboot.hunters_league.web.error.UserLicenseExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final UserService userService;
    private final CompetitionService competitionService;

    public ParticipationService(ParticipationRepository participationRepository, UserService userService, CompetitionService competitionService) {
        this.participationRepository = participationRepository;
        this.userService = userService;
        this.competitionService = competitionService;
    }

    public Participation inscription(ParticipationDTO participationDTO) {
        User user = userService.findById(participationDTO.getUser_id());
        Competition competition = competitionService.findById(participationDTO.getCompetition_id());
        verificationOfLicenseExpiration(user, competition);
        checkIfTheRegistrationIsOpenForTheCompetition(competition);
        Participation participation = Participation.builder().user(user).competition(competition).score(0.0).build();
        return participationRepository.save(participation);
    }

    public void verificationOfLicenseExpiration(User user, Competition competition) {
        if (!user.getLicenseExpirationDate().isAfter(competition.getDate())) {
            throw new UserLicenseExpiredException();
        }
    }

    public void checkIfTheRegistrationIsOpenForTheCompetition(Competition competition) {
            if (!competition.getOpenRegistration()) {
                throw new CompetitionRegistrationClosedException();
            }
    }

    public Page<Participation> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("Competition.date").descending());
        return participationRepository.findAll(pageable);

    }

    public Participation findById(UUID id) {
        return participationRepository.findById(id).orElseThrow(ParticipationNotFoundException::new);
    }

    public Double calculateScore(UUID id) {
        Participation participation = findById(id);
        Double score = participation.getHunts().stream().mapToDouble(hunt -> hunt.getSpecies().getPoints() + hunt.getWeight() * hunt.getSpecies().getCategory().getValue() * hunt.getSpecies().getDifficulty().getValue()).sum();
        participation.setScore(score);
        participationRepository.save(participation);
        return score;
    }

    public List<Participation> findByCriteria(UUID userId, UUID competitionId) {

        Specification<Participation> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();


            if (userId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), userId));
            }


            if (competitionId != null) {
                predicates.add(cb.equal(root.get("competition").get("id"), competitionId));
            }

            if (predicates.isEmpty()) {
                throw new NoSearchCriteriaException();
            }

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return participationRepository.findAll(spec);
    }

    public List<Participation> competitionPodium(UUID competitionId) {
        competitionService.findById(competitionId);
        return participationRepository.findTop3ByCompetition(competitionId);
    }

    public List<Participation> findParticipationHistory(UUID userId) {
        userService.findById(userId);
        return participationRepository.findParticipationHistory(userId);
    }

    public void delete(UUID id) {
        participationRepository.deleteParticipationWithHunts(id);
    }
}
