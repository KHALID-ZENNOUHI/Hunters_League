package org.springboot.hunters_league.repository;

import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, UUID>, JpaSpecificationExecutor<Participation> {
    Page<Participation> findAll(Pageable pageable);

    List<Participation> findByUser(User user);

    @Query("SELECT p FROM Participation p WHERE p.competition.id = :competitionId ORDER BY p.score DESC LIMIT 3")
    List<Participation> findTop3ByCompetition(UUID competitionId);

    @Query("SELECT p FROM Participation p JOIN FETCH p.competition c WHERE p.user.id = :userId AND c.date < CURRENT_TIMESTAMP ORDER BY c.date DESC")
    List<Participation> findParticipationHistory(@Param("userId") UUID userId);

    @Procedure(name = "DeleteParticipationWithHunts")
    void deleteParticipationWithHunts(@Param("id") UUID id);
}
