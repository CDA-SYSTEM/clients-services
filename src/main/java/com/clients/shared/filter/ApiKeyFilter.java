package com.clients.shared.filter;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.databind.ObjectMapper;
>>>>>>> 0acdf871a73ff41bf7b5b21724a9294331765599
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> 0acdf871a73ff41bf7b5b21724a9294331765599

@Component
@Order(1)
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String apiKey;
<<<<<<< HEAD

    public ApiKeyFilter(@Value("${app.api-key}") String apiKey) {
        this.apiKey = apiKey;
=======
    private final ObjectMapper objectMapper;

    public ApiKeyFilter(@Value("${app.api-key}") String apiKey, ObjectMapper objectMapper) {
        this.apiKey = apiKey;
        this.objectMapper = objectMapper;
>>>>>>> 0acdf871a73ff41bf7b5b21724a9294331765599
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestApiKey = request.getHeader("x-api-key");

        if (requestApiKey == null || !requestApiKey.equals(apiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
<<<<<<< HEAD
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\":false,\"message\":\"API key invalida o ausente\"}");
=======
            objectMapper.writeValue(response.getWriter(),
                    Map.of("success", false, "message", "API key invalida o ausente"));
>>>>>>> 0acdf871a73ff41bf7b5b21724a9294331765599
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
<<<<<<< HEAD
        return path.equals("/")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
=======
        return path.equals("/");
>>>>>>> 0acdf871a73ff41bf7b5b21724a9294331765599
    }
}
