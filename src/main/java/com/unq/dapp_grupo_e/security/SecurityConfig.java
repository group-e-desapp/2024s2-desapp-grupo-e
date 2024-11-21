package com.unq.dapp_grupo_e.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.unq.dapp_grupo_e.exceptions.UserNotFoundException;
import com.unq.dapp_grupo_e.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private UserRepository userRepo;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public SecurityConfig(UserRepository userRepository, JwtAuthenticationFilter jwtAuthentication) {
        this.userRepo = userRepository;
        this.jwtAuthenticationFilter = jwtAuthentication;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
 
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new CustomUserDetailsService(userRepo));
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepo.findByEmail(email)
            .orElseThrow(UserNotFoundException::new);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/authUser/**","/doc/swagger-ui/**",
                    "/v3/api-docs/**","/h2-console/**","/transaction/**", "/crypto/**", "/actuator/prometheus", "/actuator",
                    "/actuator/health", "/actuator/metrics", "/actuator/metrics/**").permitAll()
                .anyRequest().authenticated())
            .sessionManagement(sessionManager->
                sessionManager
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider()) 
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    
}
