package org.springboot.hunters_league.service;

import lombok.RequiredArgsConstructor;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.domain.Enum.Role;
import org.springboot.hunters_league.repository.UserRepository;
import org.springboot.hunters_league.util.JwtUtil;
import org.springboot.hunters_league.util.PasswordHash;
import org.springboot.hunters_league.web.error.InvalidUsernameOrPasswordException;
import org.springboot.hunters_league.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtService;
    private final MyUserDetailService userDetailsService;

    public User save(User user) {
        user.setJoinDate(java.time.LocalDateTime.now());
        user.setLicenseExpirationDate(java.time.LocalDateTime.now().plusYears(2));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verify(User user) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return jwt;
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User login(User user) {
        return userRepository.findByUsername(user.getUsername())
                .filter(u -> PasswordHash.checkPassword(user.getPassword(), u.getPassword()))
                .orElseThrow(InvalidUsernameOrPasswordException::new);
    }

    public User update(User user) {
        findById(user.getId());
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        findById(id);
        userRepository.deleteUser(id);
    }

    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username"));
        return userRepository.findAll(pageable);
    }


    public Page<User> search(String username, String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.search(username, email, pageable);
    }
}
