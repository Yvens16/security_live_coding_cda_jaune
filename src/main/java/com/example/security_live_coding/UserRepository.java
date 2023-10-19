package com.example.security_live_coding;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByUsername(String username);

  // Boolean existsByUsername(String username);

  // Boolean existsByEmail(String email); Est un exemple de existBy un autrre champs que le username

}
