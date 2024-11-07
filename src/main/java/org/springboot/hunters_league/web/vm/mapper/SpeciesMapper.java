package org.springboot.hunters_league.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springboot.hunters_league.domain.Species;
import org.springboot.hunters_league.web.vm.requestVM.SpeciesSaveVM;
import org.springboot.hunters_league.web.vm.requestVM.SpeciesUpdateVM;
import org.springboot.hunters_league.web.vm.responseVM.SpeciesVM;

@Mapper(componentModel = "spring")
public interface SpeciesMapper {
    SpeciesMapper Instance = Mappers.getMapper(SpeciesMapper.class);

    SpeciesSaveVM speciesToSpeciesSaveVM(Species species);

    Species speciesSaveVMToSpecies(SpeciesSaveVM speciesSaveVM);

    Species speciesUpdateVMToSpecies(SpeciesUpdateVM speciesUpdateVM);

    SpeciesUpdateVM speciesToSpeciesUpdateVM(Species species);

    SpeciesVM speciesToSpeciesVM(Species species);

    Species speciesVMToSpecies(SpeciesVM speciesVM);


}
