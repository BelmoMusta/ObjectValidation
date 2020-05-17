package io.github.belmomusta.validation.utils;

import io.github.belmomusta.validation.exception.ValidationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * class of reflection utilities
 */
public class ReflectUtils {

    /**
     * The default constructor
     */
    private ReflectUtils() {
    }

    /**
     * @param aClass          the class to check that it has annotated by the annotation class
     * @param <T>             the generic type of the object
     * @param annotationClass the annotation class
     * @return <code> List&lt;Field&gt;</code> a list of the annotated fields with
     * <code> Validation annotation </code> in the given class
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Field> getAnnotatedFields(Class<? extends T> aClass, Class annotationClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(annotationClass)) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * @param object    the object
     * @param fieldPath the path of the field
     * @return Object
     * @throws NoSuchFieldException   if the field path leads to a field that does'nt exist
     * @throws IllegalAccessException if the reflection throws an illegal access exception
     * @throws ValidationException if the reflection throws an illegal access exception
     */
    public static Object getFieldValue(Object object, String fieldPath) throws NoSuchFieldException, IllegalAccessException, ValidationException {
        Object next = object;
        String[] fields = fieldPath.split("\\.");

        int i = 0;
        while (next != null && i < fields.length) {
            String fieldName = fields[i];
            if (fieldName.matches("\\w+\\[\\d+]")) {
                int first = fieldName.indexOf('[');
                int last = fieldName.indexOf(']');
                String realFieldName = fieldName.substring(0, first);
                Integer index = Integer.valueOf(fieldName.substring(first + 1, last));
                Class cls = next.getClass();
                Field field = cls.getDeclaredField(realFieldName);
                field.setAccessible(true);
                next = field.get(next);
                next = ArrayUtils.get(next, index);
            } else {
                Class cls = next.getClass();
                Field field = cls.getDeclaredField(fieldName);
                field.setAccessible(true);
                next = field.get(next);
            }
            i++;
        }
        return next;
    }
}
