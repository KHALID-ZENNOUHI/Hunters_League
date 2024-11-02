package org.springboot.hunters_league.repository;

import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
    Page<Participation> findAll(Pageable pageable);
    List<Participation> findByUser(User user);
}
