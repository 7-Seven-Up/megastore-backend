package com._up.megastore.data.model;

import com._up.megastore.data.enums.Role;
import com._up.megastore.data.pipes.PhoneNumberConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

  @NonNull
  private String username;

  @NonNull
  private String password;

  @NonNull
  private String fullName;

  @NonNull
  private String email;

  @NonNull
  @Convert(converter = PhoneNumberConverter.class)
  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Role role = Role.USER;

  @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
  private List<Address> addresses = Collections.emptyList();

  @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
  private List<Order> orders = Collections.emptyList();

  private boolean deleted = false;
  private boolean activated = false;
  private final UUID activationToken = UUID.randomUUID();

  private UUID recoverPasswordToken = null;

  @Id
  private final UUID userId = UUID.randomUUID();

}