package com.ecommerce.authservice.controllers;

import com.ecommerce.authservice.dtos.LoginRequestDto;
import com.ecommerce.authservice.dtos.SignupRequestDto;
import com.ecommerce.authservice.dtos.UserResponseDto;
import com.ecommerce.authservice.models.Token;
import com.ecommerce.authservice.models.User;
import com.ecommerce.authservice.services.AuthService;
import com.ecommerce.authservice.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public UserResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        User user = authService.signup(
                signupRequestDto.getName(),
                signupRequestDto.getEmail(),
                signupRequestDto.getPassword(),
                signupRequestDto.getPhoneNumber()
        );

        return from(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        Token token = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return new ResponseEntity<>(token.getValue(), HttpStatus.OK);
    }

    @GetMapping("/validate")
    public UserResponseDto validateToken(@RequestHeader("token") String tokenValue) {
        User user = authService.validateToken(tokenValue);
        return from(user);
    }

    private UserResponseDto from(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

}
