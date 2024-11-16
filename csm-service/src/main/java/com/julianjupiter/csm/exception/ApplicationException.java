package com.julianjupiter.csm.exception;

import com.julianjupiter.csm.dto.ErrorMessageDto;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian Jupiter
 */
public class ApplicationException extends RuntimeException {
    private final HttpStatusCode httpStatusCode;
    private final List<ErrorMessageDto> errorMessages = new ArrayList<>();

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

    public List<ErrorMessageDto> errorMessages() {
        return errorMessages;
    }

    public ApplicationException errorMessages(List<ErrorMessageDto> errorMessages) {
        this.errorMessages.addAll(errorMessages);
        return  this;
    }
}
