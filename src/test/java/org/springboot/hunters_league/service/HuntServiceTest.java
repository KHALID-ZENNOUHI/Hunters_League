package org.springboot.hunters_league.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springboot.hunters_league.domain.Hunt;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.Species;
import org.springboot.hunters_league.repository.HuntRepository;
import org.springboot.hunters_league.service.dto.HuntDTO;
import org.springboot.hunters_league.web.error.WeightNotApprovedException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class HuntServiceTest {

    @Mock
    private HuntRepository huntRepository;

    @Mock
    private SpeciesService speciesService;

    @Mock
    private ParticipationService participationService;

    @InjectMocks
    private HuntService huntService;

    private HuntDTO huntDTO;
    private Species species;
    private Participation participation;
    private Hunt hunt;
    private UUID participationId;
    private UUID speciesId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participationId = UUID.randomUUID();
        speciesId = UUID.randomUUID();

        species = new Species();
        species.setId(speciesId);
        species.setMinimumWeight(10.0);

        participation = new Participation();
        participation.setId(participationId);

        huntDTO = new HuntDTO();
        huntDTO.setSpecies_id(speciesId);
        huntDTO.setParticipation_id(participationId);
        huntDTO.setWeight(15.0);

        hunt = Hunt.builder()
                .species(species)
                .participation(participation)
                .weight(15.0)
                .build();
    }

    @Test
    void testSaveHunt_Success() {
        when(speciesService.findById(any())).thenReturn(species);
        when(participationService.findById(any())).thenReturn(participation);
        when(huntRepository.save(any())).thenReturn(hunt);

        // Mock calculateScore to return a value since it is not void
        when(participationService.calculateScore(any())).thenReturn(0.0);

        Hunt savedHunt = huntService.save(huntDTO);

        assertNotNull(savedHunt);
        assertEquals(15.0, savedHunt.getWeight());
        verify(huntRepository, times(1)).save(any());
        verify(participationService, times(1)).calculateScore(participationId);
    }



    @Test
    void testSaveHunt_ThrowsWeightNotApprovedException() {
        huntDTO.setWeight(5.0); // Below minimum weight
        when(speciesService.findById(any())).thenReturn(species);

        assertThrows(WeightNotApprovedException.class, () -> huntService.save(huntDTO));
        verify(huntRepository, never()).save(any());
        verify(participationService, never()).calculateScore(any());
    }
}
