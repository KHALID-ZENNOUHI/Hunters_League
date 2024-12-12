package org.springboot.hunters_league.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.service.UserService;
import org.springboot.hunters_league.web.vm.mapper.UserMapper;
import org.springboot.hunters_league.web.vm.requestVM.LoginVM;
import org.springboot.hunters_league.web.vm.requestVM.RegisterVM;
import org.springboot.hunters_league.web.vm.requestVM.UserVM;
import org.springboot.hunters_league.web.vm.responseVM.ProfileVM;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserMapper userMapper;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ProfileVM> register(@Valid @RequestBody RegisterVM registerVM) {
        User user = userMapper.registerToUser(registerVM);
        User user1   = userService.save(user);
        ProfileVM profileVM = userMapper.userToProfileVM(user1);
        return ResponseEntity.ok(profileVM);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginVM loginVM) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword())
        );
        User loginToUser = userMapper.loginToUser(loginVM);
        String jwt = userService.verify(loginToUser);
        return ResponseEntity.ok(jwt);
    }
}
