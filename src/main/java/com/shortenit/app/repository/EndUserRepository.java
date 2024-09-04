package com.shortenit.app.repository;

import com.shortenit.app.persistence.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, UUID> {
  boolean existsByEmail(String email);
}
