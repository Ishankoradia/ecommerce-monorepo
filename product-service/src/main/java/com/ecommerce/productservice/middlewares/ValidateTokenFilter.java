package com.ecommerce.productservice.middlewares;

import com.ecommerce.productservice.dtos.AuthenticatedUserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Objects;

@WebFilter(urlPatterns = "/*")
public class ValidateTokenFilter implements Filter {
    private RestTemplate restTemplate;

    public ValidateTokenFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");

        String authServiceBaseUrl = System.getProperty("AUTH_SERVICE_URI");
        if (authServiceBaseUrl == null) {
            throw new ServletException("Auth service base URL not configured in environment variables");
        }

        if (Objects.equals(token, System.getProperty("DEFAULT_AUTH_TOKEN"))) {
            // If the token is the default token, allow the request to proceed
            filterChain.doFilter(servletRequest, servletResponse);
        }

        Boolean isValid = false;
        try {
            String authServiceUrl = authServiceBaseUrl + "/validate";

            HttpHeaders headers = new HttpHeaders();
            headers.set("token", token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<AuthenticatedUserDto> validatedUser = restTemplate.exchange(
                    authServiceUrl,
                    HttpMethod.GET,
                    entity,
                    AuthenticatedUserDto.class
            );

            if (validatedUser.getStatusCode().is2xxSuccessful() && validatedUser.getBody() != null) {
                isValid = true;
            } else {
                isValid = false;
            }

        } catch (Exception e) {
            // Log or handle exception as needed
            isValid = false;
        }

        // Optionally, you can block the request if the token is invalid
        if (!isValid) {
            servletResponse.setContentType("application/json");
            servletResponse.getWriter().write("{\"error\":\"Invalid token\"}");
            ((ServletResponse) servletResponse).getWriter().flush();
            return;
        }

        // continue the chain
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

