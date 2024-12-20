package org.springboot.hunters_league.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springboot.hunters_league.domain.Competition;
import org.springboot.hunters_league.repository.CompetitionRepository;
import org.springboot.hunters_league.web.error.CompetitionNotFoundException;
import org.springboot.hunters_league.web.error.ExistCompetitionInTheSameWeekException;
import org.springboot.hunters_league.web.error.InvalidParticipantsRangeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompetitionServiceTest {
    @Mock
    private CompetitionRepository competitionRepository;

    @Mock
    private TaskScheduler taskScheduler;

    @InjectMocks
    private CompetitionService competitionService;

    private Competition competition;
    private UUID competitionId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        competitionId = UUID.randomUUID();
        competition = new Competition();
        competition.setId(competitionId);
        competition.setLocation("London");
        competition.setDate(LocalDate.of(2024, 10, 14).atStartOfDay());
        competition.setMinParticipants(10);
        competition.setMaxParticipants(50);
        competition.setOpenRegistration(true);
    }

    @Test
    void testSaveCompetition_Success() {
        when(competitionRepository.findCompetitionInSameWeek(any())).thenReturn(Optional.empty());
        when(competitionRepository.save(any())).thenReturn(competition);

        Competition savedCompetition = competitionService.save(competition);

        assertNotNull(savedCompetition);
        assertEquals("london_14_10_2024", savedCompetition.getCode());
        verify(competitionRepository, times(1)).save(any());
    }

    @Test
    void testSaveCompetition_ThrowsExistCompetitionInTheSameWeekException() {
        when(competitionRepository.findCompetitionInSameWeek(any())).thenReturn(Optional.of(competition));

        assertThrows(ExistCompetitionInTheSameWeekException.class, () -> competitionService.save(competition));
    }

    @Test
    void testSaveCompetition_ThrowsInvalidParticipantsRangeException() {
        competition.setMinParticipants(60); // Invalid range
        assertThrows(InvalidParticipantsRangeException.class, () -> competitionService.save(competition));
    }

    @Test
    void testFindById_Success() {
        when(competitionRepository.findById(any())).thenReturn(Optional.of(competition));

        Competition foundCompetition = competitionService.findById(competitionId);
        assertNotNull(foundCompetition);
        assertEquals(competitionId, foundCompetition.getId());
    }

    @Test
    void testFindById_ThrowsCompetitionNotFoundException() {
        when(competitionRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CompetitionNotFoundException.class, () -> competitionService.findById(competitionId));
    }

    @Test
    void testUpdateCompetition_Success() {
        when(competitionRepository.findById(any())).thenReturn(Optional.of(competition));
        when(competitionRepository.save(any())).thenReturn(competition);

        Competition updatedCompetition = competitionService.update(competition);
        assertNotNull(updatedCompetition);
        verify(competitionRepository, times(1)).save(any());
    }

    @Test
    void testDeleteCompetition() {
        doNothing().when(competitionRepository).deleteById(any());

        assertDoesNotThrow(() -> competitionService.delete(competitionId));
        verify(competitionRepository, times(1)).deleteById(competitionId);
    }

    @Test
    void testFindAllCompetitions() {
        Page<Competition> page = new PageImpl<>(Collections.singletonList(competition));
        when(competitionRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<Competition> result = competitionService.findAll(0, 10);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
}
