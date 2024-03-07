package com.example.flightbookingservice.controller;

import com.example.flightbookingservice.dto.AuthDto;
import com.example.flightbookingservice.dto.EmailValueDTO;
import com.example.flightbookingservice.dto.LoginRequest;
import com.example.flightbookingservice.dto.RegisterUserDto;
import com.example.flightbookingservice.service.TravelUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping({ "/api/v1/" })
@RestController
@AllArgsConstructor
public class AuthController
{
    private final TravelUserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping( "/token/" )
    public AuthDto token(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword()));
        return this.userService.getTokenForUser(authentication);
    }

    @PostMapping({ "/register/" })
    public void register(@RequestBody RegisterUserDto user) {
        this.userService.createUser(user);
    }

    @GetMapping({ "/checkmail/" })
    public EmailValueDTO emailExists(@RequestParam final String email) {
        return this.userService.checkEmailExists(email);
    }

    @PostMapping("/checkuser")
    public EmailValueDTO checkUser(@RequestBody LoginRequest request){
        return userService.checkCrediantials(request);
    }

}

