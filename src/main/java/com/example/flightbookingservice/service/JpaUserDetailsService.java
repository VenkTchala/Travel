package com.example.flightbookingservice.service;

import com.example.flightbookingservice.repository.TravelUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService
{
    TravelUserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.getTravelUserByEmail(email)
                .map(com.example.flightbookingservice.config.SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("UserName not Found"));
    }
}

