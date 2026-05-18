package com.clients.shared.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@Order(2)
public class SwaggerAuthFilter extends OncePerRequestFilter {

    private static final String SWAGGER_AUTH_SESSION_ATTR = "swaggerAuthenticated";

    private final String swaggerUser;
    private final String swaggerPassword;

    public SwaggerAuthFilter(@Value("${app.swagger.user}") String swaggerUser,
                             @Value("${app.swagger.password}") String swaggerPassword) {
        this.swaggerUser = swaggerUser;
        this.swaggerPassword = swaggerPassword;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        HttpSession session = request.getSession();
        Boolean authenticated = (Boolean) session.getAttribute(SWAGGER_AUTH_SESSION_ATTR);

        if (Boolean.TRUE.equals(authenticated)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            reject(response);
            return;
        }

        if (!isValidCredentials(authHeader)) {
            reject(response);
            return;
        }

        session.setAttribute(SWAGGER_AUTH_SESSION_ATTR, true);
        filterChain.doFilter(request, response);
    }

    private boolean isValidCredentials(String authHeader) {
        try {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] parts = credentials.split(":", 2);
            return parts.length == 2
                    && parts[0].equals(swaggerUser)
                    && parts[1].equals(swaggerPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void reject(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"Swagger UI\"");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":false,\"message\":\"Credenciales Swagger invalidas\"}");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.startsWith("/swagger-ui") && !path.startsWith("/v3/api-docs");
    }
}
