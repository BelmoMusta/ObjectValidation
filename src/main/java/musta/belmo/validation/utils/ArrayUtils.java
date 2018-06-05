package musta.belmo.validation.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by DELL on 01/06/2018.
 */
public class ArrayUtils {
    private ArrayUtils() {
    }

    public static String toString(Object currentValue) {
        List list = castArrayToList(currentValue);
        if (list == null)
            return "#null#";
        return list.toString();
    }

    public static Object get(Object currentValue, int index) {
        List list = castArrayToList(currentValue);
        if (list != null && !list.isEmpty() && list.size() > index)
            return list.get(index);
        return null;
    }

    public static List castArrayToList(Object currentValue) {
        List list = null;
        if (currentValue instanceof int[]) {
            list = Arrays.asList(toBoxedArray((int[]) currentValue));
        } else if (currentValue instanceof long[]) {
            list = Arrays.asList(toBoxedArray((long[]) currentValue));
        } else if (currentValue instanceof byte[]) {
            list = Arrays.asList(toBoxedArray((byte[]) currentValue));
        } else if (currentValue instanceof boolean[]) {
            list = Arrays.asList(toBoxedArray((boolean[]) currentValue));
        } else if (currentValue instanceof float[]) {
            list = Arrays.asList(toBoxedArray((float[]) currentValue));
        } else if (currentValue instanceof short[]) {
            list = Arrays.asList(toBoxedArray((short[]) currentValue));
        } else if (currentValue instanceof double[]) {
            list = Arrays.asList(toBoxedArray((double[]) currentValue));
        } else if (currentValue instanceof char[]) {
            list = Arrays.asList(toBoxedArray((char[]) currentValue));
        } else if (currentValue instanceof Collection) {
            list = new ArrayList((Collection) currentValue);
        }
        return list;
    }

    public static Boolean[] toBoxedArray(boolean[] input) {
        Boolean[] boxedArray = null;
        if (input != null) {
            boxedArray = new Boolean[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }

    public static Byte[] toBoxedArray(byte[] input) {
        Byte[] boxedArray = null;
        if (input != null) {
            boxedArray = new Byte[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }

    public static Double[] toBoxedArray(double[] input) {
        Double[] boxedArray = null;
        if (input != null) {
            boxedArray = new Double[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }

    public static Character[] toBoxedArray(char[] input) {
        Character[] boxedArray = null;
        if (input != null) {
            boxedArray = new Character[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }

    public static Short[] toBoxedArray(short[] input) {
        Short[] boxedArray = null;
        if (input != null) {
            boxedArray = new Short[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }

    public static Float[] toBoxedArray(float[] input) {
        Float[] boxedArray = null;
        if (input != null) {
            boxedArray = new Float[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }

    public static Integer[] toBoxedArray(int[] input) {
        Integer[] boxedArray = null;
        if (input != null) {
            boxedArray = new Integer[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }

    public static Long[] toBoxedArray(long[] input) {
        Long[] boxedArray = null;
        if (input != null) {
            boxedArray = new Long[input.length];
            for (int i = 0; i < input.length; i++) {
                boxedArray[i] = input[i];
            }
        }
        return boxedArray;
    }
}
