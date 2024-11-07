package org.springboot.hunters_league.web.vm.responseVM;

import lombok.Getter;
import lombok.Setter;
import org.springboot.hunters_league.domain.Enum.Role;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileVM {

    private String username;

    private Role role;


    private String firstName;


    private String lastName;

    private String cin;


    private String email;

    private String nationality;

    private LocalDateTime joinDate;


    private LocalDateTime licenseExpirationDate;

}
