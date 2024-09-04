package com.shortenit.app.persistence;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "end_users_db")
public class EndUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;
  private String email;

  // Default constructor
  public EndUser() {}

  // Constructor that sets name and email
  public EndUser(String name, String email) {
    this.name = name;
    this.email = email;
  }

  // Getters and setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
