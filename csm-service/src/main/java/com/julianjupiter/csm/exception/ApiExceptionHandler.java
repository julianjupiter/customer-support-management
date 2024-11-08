package com.julianjupiter.csm.exception;

import com.julianjupiter.csm.util.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @author Julian Jupiter
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail badCredentialsException(HttpServletRequest request, BadCredentialsException exception) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setType(AppUtil.problemDetailTypeUri(request, HttpStatus.UNAUTHORIZED, 1001));
        problemDetail.setDetail("Bad credentials");
        problemDetail.setProperties(Map.of(
                "code", 1001
        ));
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail defaultException(HttpServletRequest request, Exception exception) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(AppUtil.problemDetailTypeUri(request, HttpStatus.INTERNAL_SERVER_ERROR, 1000));
        problemDetail.setDetail("Error occurred");
        problemDetail.setProperties(Map.of(
                "code", 1000
        ));
        return problemDetail;
    }
}
