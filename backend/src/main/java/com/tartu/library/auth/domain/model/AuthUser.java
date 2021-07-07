package com.tartu.library.auth.domain.model;

import com.tartu.library.person.domain.model.Person;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class AuthUser implements UserDetails {
  /** User is reserved word in Postgres */
  String username;
  String password;
  @OneToOne Person person;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Transient private String passwordConfirm;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
