package com.ecommerce.authservice.services;

import com.ecommerce.authservice.exceptions.PasswordMismatchException;
import com.ecommerce.authservice.exceptions.UserAlreadyExistsException;
import com.ecommerce.authservice.exceptions.UserNotFoundException;
import com.ecommerce.authservice.models.User;
import com.ecommerce.authservice.repos.UserRepo;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo;

    public User signup(String name, String email, String password, String phoneNumber) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // TODO: use bcrypt
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);

        user = userRepo.save(user);

        return user;
    }

    public Pair<User, String> login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmailEquals(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Please register first");
        }

        User user = userOptional.get();
        String storedPassword = user.getPassword();
        if (!password.equals(storedPassword)) {
            throw new PasswordMismatchException("Invalid credentials");
        }

        // TODO: generate jwt

        return new Pair<>(user, "token");
    }
}
