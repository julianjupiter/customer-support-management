package com.julianjupiter.csm.util;

import com.github.f4b6a3.uuid.UuidCreator;

import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public class Uuid {
    private final UUID id;

    private Uuid(UUID id) {
        this.id = id;
    }

    public static Uuid create() {
        return new Uuid(UuidCreator.getTimeOrderedEpoch());
    }

    public static Uuid fromString(String uuid) {
        try {
            return new Uuid(UuidCreator.fromString(uuid));
        } catch (Exception exception) {
            throw new UuidException("Invalid UUID " + uuid);
        }
    }

    public UUID uuid() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public static class UuidException extends RuntimeException {
        public UuidException(String message) {
            super(message);
        }
    }
}
