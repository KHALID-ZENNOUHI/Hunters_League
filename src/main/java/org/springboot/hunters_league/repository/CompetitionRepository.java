package org.springboot.hunters_league.repository;

import org.springboot.hunters_league.domain.Competition;

import org.springboot.hunters_league.repository.dto.CompetitionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CompetitionRepository extends JpaRepository<Competition, UUID> {
    @Query(value = "SELECT * FROM competition c WHERE EXTRACT(YEAR FROM c.date) = EXTRACT(YEAR FROM CAST(:date AS TIMESTAMP)) " +
            "AND EXTRACT(WEEK FROM c.date) = EXTRACT(WEEK FROM CAST(:date AS TIMESTAMP))",
            nativeQuery = true)
    Optional<Competition> findCompetitionInSameWeek(@Param("date") LocalDateTime date);
    void deleteById(UUID id);

    Page<Competition> findAll(Pageable pageable);

    @Query("SELECT c FROM Competition c WHERE c.date >= :startOfWeek AND c.date < :endOfWeek")
    Optional<Competition> findCompetitionForCurrentWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek);

    @Query("SELECT new org.springboot.hunters_league.repository.dto.CompetitionDTO(" +
            "c.id, c.location, c.date, SIZE(c.participations)) " +
            "FROM Competition c WHERE c.id = :id")
    CompetitionDTO competitionDetails(UUID id);

}
