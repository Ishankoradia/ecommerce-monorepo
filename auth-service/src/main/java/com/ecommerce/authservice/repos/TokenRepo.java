package com.ecommerce.authservice.repos;

import com.ecommerce.authservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {


    Optional<Token> findByValueAndExpiresAtAfter(String value, Date expiresAtAfter);
}
