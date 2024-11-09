package com.julianjupiter.csm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julianjupiter.csm.entity.RoleType;
import com.julianjupiter.csm.security.JwtAuthorizationFilter;
import com.julianjupiter.csm.util.AppUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;

/**
 * @author Julian Jupiter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String[] WHITE_LIST_URL = {
            "/assets/**",
            "/problem-detail/**",
            "/api/v1/auth/login",
            "/api/v1/auth/register"
    };
    private final UserDetailsService userDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final ObjectMapper objectMapper;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthorizationFilter jwtAuthorizationFilter,
                          ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.objectMapper = objectMapper;
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(this.userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        var providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${application.security.id-for-encode}") String idForEncode) {
        var encoders = Map.of(
                "argon2@SpringSecurity_v5_8", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8(),
                "bcrypt", new BCryptPasswordEncoder(),
                "pbkdf2@SpringSecurity_v5_8", Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8(),
                "scrypt@SpringSecurity_v5_8", SCryptPasswordEncoder.defaultsForSpringSecurity_v5_8()
        );

        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var admin = RoleType.ROLE_ADMIN.name().substring(5);
        var agent = RoleType.ROLE_AGENT.name().substring(5);

        httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/tickets").hasRole(agent) // admin cannot create ticket, only agent
                        .requestMatchers(HttpMethod.GET, "/api/v1/tickets/**").hasAnyRole(admin, agent)
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tickets/**").hasAnyRole(admin, agent)
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tickets/**").hasAnyRole(admin, agent)
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            var httpStatus = HttpStatus.valueOf(HttpServletResponse.SC_FORBIDDEN);

                            var problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
                            problemDetail.setType(AppUtil.problemDetailTypeUri(request, httpStatus));
                            problemDetail.setDetail(accessDeniedException.getMessage());
                            problemDetail.setInstance(AppUtil.requestForwardUri(request));

                            var jsonString = this.objectMapper.writeValueAsString(problemDetail);

                            response.setStatus(httpStatus.value());
                            response.addHeader("Content-Type", "application/json");
                            response.getWriter().write(jsonString);
                        })
                )
                .sessionManagement(authorize -> authorize
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(this.jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
