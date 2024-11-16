package com.julianjupiter.csm.exception;

import com.julianjupiter.csm.util.AppUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @author Julian Jupiter
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ProblemDetail badCredentialsException(HttpServletRequest request, BadCredentialsException exception) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setType(AppUtil.problemDetailTypeUri(request, HttpStatus.UNAUTHORIZED));
        problemDetail.setDetail("Invalid username or password");

        return problemDetail;
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ProblemDetail> applicationException(HttpServletRequest request, ApplicationException exception) {
        var httpStatusCode = exception.httpStatusCode();

        var problemDetail = ProblemDetail.forStatus(httpStatusCode);
        problemDetail.setType(AppUtil.problemDetailTypeUri(request, (HttpStatus) httpStatusCode));
        problemDetail.setDetail(exception.getMessage());

        if (!exception.errorMessages().isEmpty()) {
            problemDetail.setProperties(Map.ofEntries(
                    Map.entry("errors", exception.errorMessages())
            ));
        }

        return new ResponseEntity<>(problemDetail, httpStatusCode);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail defaultException(HttpServletRequest request, Exception exception) {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(AppUtil.problemDetailTypeUri(request, HttpStatus.INTERNAL_SERVER_ERROR));
        problemDetail.setDetail("Error occurred");

        return problemDetail;
    }
}
