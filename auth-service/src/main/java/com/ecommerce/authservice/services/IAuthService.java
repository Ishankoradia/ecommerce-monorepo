package com.ecommerce.authservice.services;

import com.ecommerce.authservice.models.Token;
import com.ecommerce.authservice.models.User;
import org.antlr.v4.runtime.misc.Pair;

public interface IAuthService {

    User signup(String name, String email, String password, String phoneNumber);

    Token login(String email, String password);

    User validateToken(String token);
}
