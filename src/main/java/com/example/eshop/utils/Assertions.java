package com.example.eshop.utils;

import lombok.experimental.UtilityClass;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@UtilityClass
public class Assertions {

    public static @NonNull
    <T> T assertNonNull(@Nullable T value, @Nullable String msg) {
        if (value == null)
            throw new IllegalArgumentException(msg);
        return value;
    }
}
