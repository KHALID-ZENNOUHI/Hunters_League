package org.springboot.hunters_league.web.vm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.web.vm.requestVM.LoginVM;
import org.springboot.hunters_league.web.vm.requestVM.RegisterVM;
import org.springboot.hunters_league.web.vm.requestVM.UserVM;
import org.springboot.hunters_league.web.vm.responseVM.ProfileVM;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    LoginVM userToLogin(User user);

    User loginToUser(LoginVM loginVM);

    ProfileVM userToProfileVM(User user);

    User profileVMToUser(ProfileVM profileVM);

    RegisterVM userToRegister(User user);

    User registerToUser(RegisterVM registerVM);

    User userVMToUser(UserVM userVM);

    UserVM userToUserVM(User user);
}
