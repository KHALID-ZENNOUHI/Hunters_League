package org.springboot.hunters_league.service;

import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.repository.ParticipationRepository;
import org.springboot.hunters_league.service.dto.ParticipationDTO;
import org.springboot.hunters_league.web.error.CompetitionRegistrationClosedException;
import org.springboot.hunters_league.web.error.UserLicenseExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
}
