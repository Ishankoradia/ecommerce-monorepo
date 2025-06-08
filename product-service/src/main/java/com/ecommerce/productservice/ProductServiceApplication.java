package com.ecommerce.productservice;

import com.ecommerce.productservice.exceptions.BaseException;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        // Load .env variables and set as system properties
        Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("AUTH_SERVICE_URI", dotenv.get("AUTH_SERVICE_URI"));
        System.setProperty("DEFAULT_AUTH_TOKEN", dotenv.get("DEFAULT_AUTH_TOKEN"));


        SpringApplication.run(ProductServiceApplication.class, args);
    }

}
