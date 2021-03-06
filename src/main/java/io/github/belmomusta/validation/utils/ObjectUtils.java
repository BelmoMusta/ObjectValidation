package io.github.belmomusta.validation.utils;

import java.util.Optional;

/**
 * TODO: Complete the description of this class
 *
 * @author default author
 * @since 0.0.0.SNAPSHOT
 * @version 0.0.0
 */
public class ObjectUtils {

    /**
     * Gets a value if not null, another default otherwise
     *
     * @param t t
     * @param defaultValue def
     * @param <T> generic type
     * @return T
     */
    public static <T> T getOrDefault(T t, T defaultValue) {
        return Optional.ofNullable(t).orElse(defaultValue);
    }

    /**
     * get a value if a condition is veirifed
     *
     * @param condition the condition param
     * @param defaultValue the default value
     * @param <T> generic type
     * @return T
     */
    public static <T> T getIf(boolean condition, T defaultValue) {
        return condition ? defaultValue : null;
    }
}
