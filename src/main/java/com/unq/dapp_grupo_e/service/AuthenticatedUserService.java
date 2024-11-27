package com.unq.dapp_grupo_e.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.unq.dapp_grupo_e.exceptions.UserNotAuthenticatedException;
import com.unq.dapp_grupo_e.exceptions.UserNotFoundException;
import com.unq.dapp_grupo_e.model.User;
import com.unq.dapp_grupo_e.repository.UserRepository;

@Service
public class AuthenticatedUserService {

    private final UserRepository userRepository;

    public AuthenticatedUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer getCurrentUserId() {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser != null && authUser.isAuthenticated() && !"anonymousUser".equals(authUser.getPrincipal())) {
            String userEmail = authUser.getName();
            User userRecovered = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
            return userRecovered.getIdUser();
        }
        throw new UserNotAuthenticatedException();
    }
    
}
