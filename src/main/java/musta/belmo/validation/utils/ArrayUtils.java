package musta.belmo.validation.utils;

import java.util.Arrays;

/**
 * Created by DELL on 01/06/2018.
 */
public class ArrayUtils {
    private ArrayUtils() {
    }

    public static String toString(Object currentValue) {
        String str = null;

        if (currentValue instanceof int[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((int[]) currentValue));
        } else if (currentValue instanceof long[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((long[]) currentValue));
        } else if (currentValue instanceof byte[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((byte[]) currentValue));
        } else if (currentValue instanceof boolean[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((boolean[]) currentValue));
        } else if (currentValue instanceof float[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((float[]) currentValue));
        } else if (currentValue instanceof short[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((short[]) currentValue));
        } else if (currentValue instanceof double[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((double[]) currentValue));
        } else if (currentValue instanceof char[]) {
            str = Arrays.toString(ArrayUtils.toBoxedArray((char[]) currentValue));
        }

        return str;
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
