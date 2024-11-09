package com.julianjupiter.csm.exception;

import org.springframework.http.HttpStatusCode;

/**
 * @author Julian Jupiter
 */
public class ApplicationException extends RuntimeException {
    private final HttpStatusCode httpStatusCode;

    public ApplicationException(HttpStatusCode httpStatusCode, String message) {
        this(httpStatusCode, message, null);
    }

    public ApplicationException(HttpStatusCode httpStatusCode, String message, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatusCode httpStatusCode() {
        return httpStatusCode;
    }
}
