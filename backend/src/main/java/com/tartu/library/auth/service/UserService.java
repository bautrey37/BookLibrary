package com.tartu.library.auth.service;

import com.tartu.library.auth.domain.model.AuthUser;
import com.tartu.library.auth.domain.repository.AuthUserRepository;
import com.tartu.library.auth.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class UserService implements UserDetailsService {
  @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired RoleRepository roleRepository;
  @Autowired AuthUserRepository authUserRepository;

  public void save(AuthUser authUser) {
    authUser.setPassword(bCryptPasswordEncoder.encode(authUser.getPassword()));
    authUser.setRoles(new HashSet<>(roleRepository.findAll()));
    authUserRepository.save(authUser);
  }

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthUser authUser = authUserRepository.findByUsername(username);
    if (authUser == null) throw new UsernameNotFoundException(username);
    return authUser;
  }
}
