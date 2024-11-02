package org.springboot.hunters_league.web.api;

import jakarta.validation.Valid;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.service.UserService;
import org.springboot.hunters_league.web.vm.mapper.UserMapper;
import org.springboot.hunters_league.web.vm.requestVM.LoginVM;
import org.springboot.hunters_league.web.vm.requestVM.RegisterVM;
import org.springboot.hunters_league.web.vm.requestVM.UserVM;
import org.springboot.hunters_league.web.vm.responseVM.ProfileVM;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    public UserController(UserMapper userMapper, UserService userService) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<ProfileVM> register(@Valid @RequestBody RegisterVM registerVM) {
        User user = userMapper.registerToUser(registerVM);
        User savedUser = userService.save(user);
        ProfileVM profileVM = userMapper.userToProfile(savedUser);
        return ResponseEntity.ok(profileVM);
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileVM> login(@Valid @RequestBody LoginVM loginVM) {
        User loginToUser = userMapper.loginToUser(loginVM);
        User user = userService.login(loginToUser);
        ProfileVM profileVM = userMapper.userToProfile(user);
        return ResponseEntity.ok(profileVM);
    }

    @PutMapping("/update")
    public ResponseEntity<UserVM> update(@Valid @RequestBody UserVM userVM) {
        User user = userMapper.userVMToUser(userVM);
        User updatedUser = userService.update(user);
        UserVM updatedUserVM = userMapper.userToUserVM(updatedUser);
        return ResponseEntity.ok(updatedUserVM);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @GetMapping("/all")
    public Page<User> findAll(@RequestParam(name = "page", required = false, defaultValue = "0") int page, @RequestParam(name = "size", required = false, defaultValue = "100") int size) {
        return userService.findAll(page, size);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProfileVM>> search(@RequestParam(name = "keyword") String keyword) {
        List<User> user = userService.search(keyword);
        List<ProfileVM> profileVM = user.stream().map(userMapper::userToProfile).toList();
        return ResponseEntity.ok(profileVM);
    }
}
