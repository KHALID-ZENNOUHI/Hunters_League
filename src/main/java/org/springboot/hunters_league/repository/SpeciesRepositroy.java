package org.springboot.hunters_league.repository;

import org.springboot.hunters_league.domain.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpeciesRepositroy extends JpaRepository<Species, UUID> {

}
