package com.julianjupiter.csm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author Julian Jupiter
 */
public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {
    private final Class<T> type;
    private final ObjectMapper objectMapper;

    private JsonBodyHandler(Class<T> type) {
        this.type = type;
        this.objectMapper = new ObjectMapper();
    }

    public static <T> JsonBodyHandler<T> of(Class<T> type) {
        return new JsonBodyHandler<>(type);
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8),
                body -> {
                    try {
                        return objectMapper.readValue(body, type);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
