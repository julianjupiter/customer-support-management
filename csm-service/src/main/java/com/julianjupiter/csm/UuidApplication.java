package com.julianjupiter.csm;

import com.julianjupiter.csm.util.Uuid;

import java.util.stream.IntStream;

/**
 * @author Julian Jupiter
 */
public class UuidApplication {
    public static void main(String[] args) {
        IntStream.range(0, 5)
                .forEach(i -> System.out.println(Uuid.create()));
    }
}
