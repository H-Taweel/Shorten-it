package com.shortenit.app.services;

import com.shortenit.app.persistence.EndUser;
import com.shortenit.app.model.EndUserRegistration;
import com.shortenit.app.repository.EndUserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EndUserService {

  private final EndUserRepository endUserRepository;

  public EndUserService(EndUserRepository endUserRepository) {
    this.endUserRepository = endUserRepository;
  }

  public EndUser create(final EndUserRegistration registration) {
    if (endUserRepository.existsByEmail(registration.email())) {
      throw new RuntimeException("EndUser already exists");
    }
    // Use constructor that sets name and email
    final EndUser endUser = new EndUser(registration.name(), registration.email());
    return endUserRepository.save(endUser);
  }

  public Optional<EndUser> get(final UUID userId) {
    return endUserRepository.findById(userId);
  }
}


// @Service
// public class EndUserService {
//    private final Map<UUID, EndUser> users = new ConcurrentHashMap<>();
//
//    public EndUser create(final EndUserRegistration registration) {
//        final UUID id = UUID.randomUUID();
//        if (users.values().stream().anyMatch(endUser ->
// endUser.email().equals(registration.email()))) {
//            throw new RuntimeException("EndUser already exists:" + id);
//        }
//        final EndUser endUser = new EndUser(id, registration.name(), registration.email());
//        users.put(id, endUser);
//        return endUser;
//    }
//
//    public EndUser get(final UUID userId) {
//        return users.get(userId);
//    }
// }
