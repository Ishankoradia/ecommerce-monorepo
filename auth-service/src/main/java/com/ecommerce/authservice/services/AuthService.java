package com.ecommerce.authservice.services;

import com.ecommerce.authservice.exceptions.InvalidTokenException;
import com.ecommerce.authservice.exceptions.PasswordMismatchException;
import com.ecommerce.authservice.exceptions.UserAlreadyExistsException;
import com.ecommerce.authservice.exceptions.UserNotFoundException;
import com.ecommerce.authservice.models.Token;
import com.ecommerce.authservice.models.User;
import com.ecommerce.authservice.repos.TokenRepo;
import com.ecommerce.authservice.repos.UserRepo;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signup(String name, String email, String password, String phoneNumber) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);

        user = userRepo.save(user);

        return user;
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Please register first");
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("Invalid credentials");
        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date dateAfter30Days = calendar.getTime();

        token.setExpiresAt(dateAfter30Days);

        return tokenRepo.save(token);
    }

    @Override
    public User validateToken(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepo.findByValueAndExpiresAtAfter(tokenValue, new Date());

        if (tokenOptional.isEmpty()) {
            return null;
        }

        return tokenOptional.get().getUser();
    }
}
