package org.springboot.hunters_league.repository;

import org.springboot.hunters_league.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    List<User> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
    Page<User> findAll(Pageable pageable);
    @Procedure(name = "DeleteUserWithDependencies")
    void deleteUserWithDependencies(@Param("id") UUID id);
}
