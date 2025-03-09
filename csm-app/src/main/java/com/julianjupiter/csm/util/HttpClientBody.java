package com.julianjupiter.csm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Julian Jupiter
 */
public class HttpClientBody {
    private HttpClientBody() {
    }

    public static HttpRequest.BodyPublisher ofForm(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        return HttpRequest.BodyPublishers.ofString(urlEncoded);
    }

    public static HttpRequest.BodyPublisher ofJson(Map<String, String> parameters) {
        String urlEncoded = parameters.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        return HttpRequest.BodyPublishers.ofString(urlEncoded);
    }

    public static HttpRequest.BodyPublisher ofJson(Object object) {
        var objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return HttpRequest.BodyPublishers.ofString(json);
    }
}
