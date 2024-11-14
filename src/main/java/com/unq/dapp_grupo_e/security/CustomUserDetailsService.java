package com.unq.dapp_grupo_e.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.unq.dapp_grupo_e.model.Role;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userPersistence;

    @Autowired
    public CustomUserDetailsService(UserRepository userPersistence) {
        this.userPersistence = userPersistence;
    }

    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roleList){
        return roleList.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userPersistence.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), 
                                                                     user.getPassword(), mapToAuthorities(List.of(user.getRole())));
    }
}
    

