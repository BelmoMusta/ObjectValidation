package io.github.belmomusta.validation.utils;

import java.util.Optional;

public class ObjectUtils {

    public static <T> T getOrDefault(T t, T defaultValue) {
        return Optional.ofNullable(t).orElse(defaultValue);
    }

    public static <T> T getIf(boolean condition, T defaultValue) {
        return condition ? defaultValue : null;
    }
}
