package com.tartu.library.auth.domain.model;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Role implements GrantedAuthority {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Long id;

  private String name;

  @Transient
  @ManyToMany(mappedBy = "roles")
  private Set<AuthUser> people;

  public Role(Long id) {
    this.id = id;
  }

  public Role(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String getAuthority() {
    return name;
  }
}
