package com.julianjupiter.csm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianjupiter.csm.service.JwtService;
import com.julianjupiter.csm.service.UserService;
import com.julianjupiter.csm.util.AppUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final JwtService jwtService;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(JwtService jwtService, UserService userService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        var accessTokenOptional = this.getAccessTokenFromRequest(request);
        if (accessTokenOptional.isPresent()) {
            var accessToken = accessTokenOptional.get();
            try {
                this.jwtService.validateToken(accessToken);
            } catch (Exception exception) {
                LOGGER.error("Invalid access token: {}", exception.getMessage());
                SecurityContextHolder.clearContext();
                this.createInvalidTokenResponse(request, response, exception);
                return;
            }
            String username = this.jwtService.extractUsername(accessToken);
            if (username != null) {
                var user = this.userService.loadUserByUsername(username);
                if (user != null) {
                    var authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    LOGGER.error("User does not exist");
                    SecurityContextHolder.clearContext();
                }
            } else {
                LOGGER.error("Unable to extract username from access token");
                SecurityContextHolder.clearContext();
            }
        } else {
            LOGGER.warn("No authorization header");
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getAccessTokenFromRequest(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return Optional.of(authorization.substring(7));
        }

        return Optional.empty();
    }

    private void createInvalidTokenResponse(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException {
        var httpStatus = HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED);

        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(AppUtil.problemDetailTypeUri(request, httpStatus));
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setInstance(AppUtil.requestForwardUri(request));

        var jsonString = this.objectMapper.writeValueAsString(problemDetail);

        response.setStatus(httpStatus.value());
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write(jsonString);
    }
}
