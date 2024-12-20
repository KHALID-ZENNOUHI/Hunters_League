package org.springboot.hunters_league.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.repository.ParticipationRepository;
import org.springboot.hunters_league.service.dto.ParticipationDTO;
import org.springboot.hunters_league.web.error.CompetitionRegistrationClosedException;
import org.springboot.hunters_league.web.error.NoSearchCriteriaException;
import org.springboot.hunters_league.web.error.ParticipationNotFoundException;
import org.springboot.hunters_league.web.error.UserLicenseExpiredException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ParticipationServiceTest {

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private UserService userService;

    @Mock
    private CompetitionService competitionService;

    @InjectMocks
    private ParticipationService participationService;

    private ParticipationDTO participationDTO;
    private User user;
    private Competition competition;
    private Participation participation;
    private UUID participationId;
    private UUID userId;
    private UUID competitionId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participationId = UUID.randomUUID();
        userId = UUID.randomUUID();
        competitionId = UUID.randomUUID();

        user = new User();
        user.setId(userId);
        user.setLicenseExpirationDate(LocalDateTime.now().plusDays(1));

        competition = new Competition();
        competition.setId(competitionId);
        competition.setDate(LocalDateTime.now().plusDays(5));
        competition.setOpenRegistration(true);

        participationDTO = new ParticipationDTO();
        participationDTO.setUser_id(userId);
        participationDTO.setCompetition_id(competitionId);

        participation = Participation.builder()
                .id(participationId)
                .user(user)
                .competition(competition)
                .score(0.0)
                .build();
    }

    @Test
    void testInscription_Success() {
        // User's license is valid
        user.setLicenseExpirationDate(LocalDateTime.now().plusDays(10));

        // Competition registration is open
        competition.setOpenRegistration(true);

        when(userService.findById(any())).thenReturn(user);
        when(competitionService.findById(any())).thenReturn(competition);
        when(participationRepository.save(any())).thenReturn(participation);

        Participation savedParticipation = participationService.inscription(participationDTO);

        assertNotNull(savedParticipation);
        assertEquals(0.0, savedParticipation.getScore());
        verify(participationRepository, times(1)).save(any());
    }


    @Test
    void testInscription_ThrowsUserLicenseExpiredException() {
        user.setLicenseExpirationDate(LocalDateTime.now().minusDays(1)); // License expired
        when(userService.findById(any())).thenReturn(user);
        when(competitionService.findById(any())).thenReturn(competition);

        assertThrows(UserLicenseExpiredException.class, () -> participationService.inscription(participationDTO));
        verify(participationRepository, never()).save(any());
    }

    @Test
    void testInscription_ThrowsCompetitionRegistrationClosedException() {
        // Ensure the user's license is valid
        user.setLicenseExpirationDate(LocalDateTime.now().plusDays(10));

        // Set competition registration to closed
        competition.setOpenRegistration(false);

        when(userService.findById(any())).thenReturn(user);
        when(competitionService.findById(any())).thenReturn(competition);

        // Verify CompetitionRegistrationClosedException is thrown
        assertThrows(CompetitionRegistrationClosedException.class,
                () -> participationService.inscription(participationDTO));

        verify(participationRepository, never()).save(any());
    }

    @Test
    void testFindById_Success() {
        when(participationRepository.findById(any())).thenReturn(Optional.of(participation));

        Participation foundParticipation = participationService.findById(participationId);
        assertNotNull(foundParticipation);
        assertEquals(participationId, foundParticipation.getId());
    }

    @Test
    void testFindById_ThrowsParticipationNotFoundException() {
        when(participationRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ParticipationNotFoundException.class, () -> participationService.findById(participationId));
    }

    @Test
    void testCalculateScore_Success() {
        participation.setHunts(Collections.emptyList());
        when(participationRepository.findById(any())).thenReturn(Optional.of(participation));

        Double score = participationService.calculateScore(participationId);
        assertEquals(0.0, score);
        verify(participationRepository, times(1)).save(any());
    }

    @Test
    void testDeleteParticipation() {
        doNothing().when(participationRepository).deleteParticipationWithHunts(any());

        assertDoesNotThrow(() -> participationService.delete(participationId));
        verify(participationRepository, times(1)).deleteParticipationWithHunts(participationId);
    }

    @Test
    void testFindAll_Success() {
        Page<Participation> page = new PageImpl<>(Collections.singletonList(participation));
        when(participationRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Participation> result = participationService.findAll(0, 10);
        assertEquals(1, result.getContent().size());
    }
}
