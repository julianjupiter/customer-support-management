package com.julianjupiter.csm.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.net.URI;

/**
 * @author Julian Jupiter
 */
public class AppUtil {
    private AppUtil() {
    }

    public static URI problemDetailTypeUri(HttpServletRequest request, HttpStatus status, int errorCode) {
        var url = URI.create(request.getRequestURL().toString());
        var port = (url.getPort() == 80) ? "" : (":" + url.getPort());

        return URI.create(url.getScheme() + "://" + url.getHost() + port + request.getContextPath() + "/problem-detail/types?http-status=" + status.value() + "&error-code=" + errorCode);
    }

    public static URI requestForwardUri(HttpServletRequest request) {
        var uri = (String) request.getAttribute("jakarta.servlet.forward.request_uri");
        if (uri != null && !uri.isBlank()) {
            return URI.create(uri);
        }

        return URI.create(request.getRequestURI());
    }
}
