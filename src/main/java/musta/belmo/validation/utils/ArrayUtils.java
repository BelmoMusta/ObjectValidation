package musta.belmo.validation.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * class of the array utilities
 */
public class ArrayUtils {
    /**
     * The default constructor
     */
    private ArrayUtils() {
    }

    /**
     * Returns the strnig format of the array
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
    public static Object get(Object array, int index) {
        List list = castArrayToList(array);
        if (list != null && !list.isEmpty() && list.size() > index)
            return list.get(index);
        return null;
    }

    /**
     * Casts the array to list
     *
     * @param array the array
     * @return List
     */
    public static List castArrayToList(Object array) {
        List list = null;
        if (array instanceof int[]) {
            list = Arrays.asList(toBoxedArray((int[]) array));
        } else if (array instanceof long[]) {
            list = Arrays.asList(toBoxedArray((long[]) array));
        } else if (array instanceof byte[]) {
            list = Arrays.asList(toBoxedArray((byte[]) array));
        } else if (array instanceof boolean[]) {
            list = Arrays.asList(toBoxedArray((boolean[]) array));
        } else if (array instanceof float[]) {
            list = Arrays.asList(toBoxedArray((float[]) array));
        } else if (array instanceof short[]) {
            list = Arrays.asList(toBoxedArray((short[]) array));
        } else if (array instanceof double[]) {
            list = Arrays.asList(toBoxedArray((double[]) array));
        } else if (array instanceof char[]) {
            list = Arrays.asList(toBoxedArray((char[]) array));
        } else if (array instanceof Collection) {
            list = new ArrayList((Collection) array);
        }
        return list;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Boolean array
     */
    public static Boolean[] toBoxedArray(boolean[] array) {
        Boolean[] boxedArray = null;
        if (array != null) {
            boxedArray = new Boolean[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Byte array
     */
    public static Byte[] toBoxedArray(byte[] array) {
        Byte[] boxedArray = null;
        if (array != null) {
            boxedArray = new Byte[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Double array
     */
    public static Double[] toBoxedArray(double[] array) {
        Double[] boxedArray = null;
        if (array != null) {
            boxedArray = new Double[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Character array
     */
    public static Character[] toBoxedArray(char[] array) {
        Character[] boxedArray = null;
        if (array != null) {
            boxedArray = new Character[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Short array
     */
    public static Short[] toBoxedArray(short[] array) {
        Short[] boxedArray = null;
        if (array != null) {
            boxedArray = new Short[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Float array
     */
    public static Float[] toBoxedArray(float[] array) {
        Float[] boxedArray = null;
        if (array != null) {
            boxedArray = new Float[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Integer array
     */
    public static Integer[] toBoxedArray(int[] array) {
        Integer[] boxedArray = null;
        if (array != null) {
            boxedArray = new Integer[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }

    /**
     * Converts an of primitive values to their boxed type
     *
     * @param array the array
     * @return a Long array
     */
    public static Long[] toBoxedArray(long[] array) {
        Long[] boxedArray = null;
        if (array != null) {
            boxedArray = new Long[array.length];
            for (int i = 0; i < array.length; i++) {
                boxedArray[i] = array[i];
            }
        }
        return boxedArray;
    }
}
