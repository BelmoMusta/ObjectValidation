package musta.belmo.validation.utils;

import musta.belmo.validation.enumeration.ErrorMessage;
import musta.belmo.validation.exception.ValidationException;

import java.util.*;
import java.util.function.Function;

/**
 * class of the array utilities
 */
public class ArrayUtils {
    /**
     * Each type has its converting function.
     */
    private static Map<Class<?>, Function> mapper;

    /**
     * The default constructor
     */
    private ArrayUtils() {
    }

    /**
     *
     */
    private static void createMapper() {
        if (mapper == null) {
            mapper = new HashMap<>();
            mapper.put(boolean[].class, toBoxedBooleanArray());
            mapper.put(byte[].class, toBoxedByteArray());
            mapper.put(double[].class, toBoxedDoubleArray());
            mapper.put(char[].class, toBoxedCharacterArray());
            mapper.put(short[].class, toBoxedShortArray());
            mapper.put(float[].class, toBoxedFloatArray());
            mapper.put(int[].class, toBoxedIntegerArray());
            mapper.put(long[].class, toBoxedLongArray());
        }
    }

    /**
     * Returns the string format of the array
     *
     * @param array the array
     * @return String
     */
    public static String toString(Object array) {
        List list = castArrayToList(array);
        if (list == null)
            return "#null#";
        return list.toString();
    }

    /**
     * Gets the value from the array at the given index
     *
     * @param array the array
     * @param index the index
     * @return Object
     */
    public static Object get(Object array, int index) throws ValidationException {
        Object value;
        List list = castArrayToList(array);

        if (list != null && !list.isEmpty() && list.size() > index) {
            value = list.get(index);

        } else if (list == null || list.isEmpty()) {
            throw new ValidationException(ErrorMessage.ARRAY_IS_NULL.getLabel());
        } else {
            throw new ValidationException(new ArrayIndexOutOfBoundsException());
        }
        return value;
    }

    /**
     * Converts an array of primitive type to its respective boxed one.
     *
     * @param array the array
     * @param <T>   the type of the target array
     * @return T[]
     */
    @SuppressWarnings("all")
    public static <T> T[] toBoxedArray(Object array) {
        T[] returnValue;
        if (array == null) {
            returnValue = null;
        } else if (Collection.class.isAssignableFrom(array.getClass())) {
            returnValue = (T[]) new ArrayList((Collection) array).toArray();
        } else {
            createMapper();
            returnValue = (T[]) mapper.get(array.getClass()).apply(array);
        }
        return returnValue;
    }

    /**
     * Casts the array to list
     *
     * @param array the array
     * @return List
     */
    public static <T> List<T> castArrayToList(Object array) {
        T[] boxedArray = toBoxedArray(array);
        return boxedArray != null ?
                Arrays.asList(boxedArray) :
                null;

    }

    /**
     * Converts an array of boolean to a boxed array of type {@link Boolean}
     *
     * @return Boolean
     */
    private static Function<boolean[], Boolean[]> toBoxedBooleanArray() {
        return array -> {
            Boolean[] boxed = new Boolean[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }

    /**
     * Converts an array of byte to a boxed array of type {@link Byte}
     *
     * @return Byte
     */
    private static Function<byte[], Byte[]> toBoxedByteArray() {
        return array -> {
            Byte[] boxed = new Byte[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }

    /**
     * Converts an array of double to a boxed array of type {@link Double}
     *
     * @return Double
     */
    private static Function<double[], Double[]> toBoxedDoubleArray() {
        return array -> {
            Double[] boxed = new Double[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }

    /**
     * Converts an array of char to a boxed array of type {@link Character}
     *
     * @return Character
     */
    private static Function<char[], Character[]> toBoxedCharacterArray() {
        return array -> {
            Character[] boxed = new Character[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }

    /**
     * Converts an array of short to a boxed array of type {@link Short}
     *
     * @return Short
     */
    private static Function<short[], Short[]> toBoxedShortArray() {
        return array -> {
            Short[] boxed = new Short[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }

    /**
     * Converts an array of float to a boxed array of type {@link Float}
     *
     * @return Float
     */
    private static Function<float[], Float[]> toBoxedFloatArray() {
        return array -> {
            Float[] boxed = new Float[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }

    /**
     * Converts an array of int to a boxed array of type {@link Integer}
     *
     * @return Integer
     */
    private static Function<int[], Integer[]> toBoxedIntegerArray() {
        return array -> {
            Integer[] boxed = new Integer[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }

    /**
     * Converts an array of long to a boxed array of type {@link Long}
     *
     * @return Long
     */
    private static Function<long[], Long[]> toBoxedLongArray() {
        return array -> {
            Long[] boxed = new Long[array.length];
            Arrays.setAll(boxed, index -> array[index]);
            return boxed;
        };
    }
}
