package com.example.flightbookingservice.service;

import com.example.flightbookingservice.dto.*;
import com.example.flightbookingservice.entity.TravelUser;
import com.example.flightbookingservice.mappers.UserMapper;
import com.example.flightbookingservice.repository.TravelUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TravelUserService {
    private final TravelUserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder encoder;

    public AuthDto getTokenForUser(final Authentication authentication) {
        final TravelUser user = this.getUserByEmail(authentication.getName());
        final UserDto result = UserMapper.MAPPER.convert(user);
        return AuthDto.builder().user(result).token(this.tokenService.generateToken(authentication)).build();
    }

    public TravelUser getUserByEmail(final String email) {
        return this.userRepository.getTravelUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("no user by email " + email));
    }


    public void createUser(final RegisterUserDto user) {
         this.userRepository.save(
                 TravelUser.builder()
                         .firstName(user.getFirstName())
                         .lastName(user.getLastName())
                         .password(this.encoder.encode(user.getPassword()))
                         .email(user.getEmail())
                         .build());
    }

    public EmailValueDTO checkEmailExists(final String email) {
        return this.userRepository.getTravelUserByEmail(email).map(i -> EmailValueDTO.builder()
                        .value(i.getEmail().equals(email))
                        .build())
                .orElse(EmailValueDTO.builder().value(false).build());

    }


    public EmailValueDTO checkCrediantials(LoginRequest request) {
        return userRepository.getTravelUserByEmail(request.getEmail()).map(u ->{
                    System.out.println(u.getPassword());
                    return EmailValueDTO.builder().value(encoder.matches(request.getPassword(),u.getPassword()))
                            .build();})
                .orElse(EmailValueDTO.builder()
                        .value(false)
                        .build());

    }
}
