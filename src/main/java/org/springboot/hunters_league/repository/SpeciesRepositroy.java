package org.springboot.hunters_league.repository;

import org.springboot.hunters_league.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpeciesRepositroy extends JpaRepository<Species, UUID>, JpaSpecificationExecutor<Species> {


}
