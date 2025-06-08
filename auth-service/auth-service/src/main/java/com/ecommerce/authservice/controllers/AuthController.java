package com.ecommerce.authservice.controllers;

import com.ecommerce.authservice.dtos.LoginRequestDto;
import com.ecommerce.authservice.dtos.SignupRequestDto;
import com.ecommerce.authservice.dtos.UserResponseDto;
import com.ecommerce.authservice.models.User;
import com.ecommerce.authservice.services.AuthService;
import com.ecommerce.authservice.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Pair<User, String> pair = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        UserResponseDto userResponseDto = from(pair.a);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    private UserResponseDto from(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }

}
