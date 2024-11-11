package org.springboot.hunters_league.repository;

import org.springboot.hunters_league.domain.Hunt;
import org.springboot.hunters_league.domain.Participation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface HuntRepository extends JpaRepository<Hunt, UUID> {
    List<Hunt> findHuntByParticipation(Participation participation);
}
