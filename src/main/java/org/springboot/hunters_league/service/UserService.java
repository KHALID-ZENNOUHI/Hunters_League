package org.springboot.hunters_league.service;

import jakarta.transaction.Transactional;
import org.springboot.hunters_league.domain.Participation;
import org.springboot.hunters_league.domain.Role;
import org.springboot.hunters_league.domain.User;
import org.springboot.hunters_league.repository.UserRepository;
import org.springboot.hunters_league.util.PasswordHash;
import org.springboot.hunters_league.web.error.InvalidUsernameOrPasswordException;
import org.springboot.hunters_league.web.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ParticipationService participationService;

    @Autowired
    public UserService(UserRepository userRepository, ParticipationService participationService) {
        this.userRepository = userRepository;
        this.participationService = participationService;
    }

    public User save(User user) {
        user.setRole(Role.MEMBER);
        user.setJoinDate(java.time.LocalDateTime.now());
        user.setLicenseExpirationDate(java.time.LocalDateTime.now().plusYears(2));
        String password = PasswordHash.hashPassword(user.getPassword());
        user.setPassword(password);
        return userRepository.save(user);
    }

    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
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
        return userRepository.save(user);
    }

    public void delete(UUID id) {
            userRepository.deleteUserWithDependencies(id);
    }

    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username"));
        return userRepository.findAll(pageable);
    }

    public List<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(String keyword) {
        return userRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(keyword, keyword);
    }

    public List<User> search(String keyword) {
        return findByUsernameIgnoreCaseOrEmailIgnoreCase(keyword);
    }
}
