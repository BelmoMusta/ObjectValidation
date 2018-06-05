package musta.belmo.validation.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtils {

    private ReflectUtils() {
    }


    /**
     * @param aClass          the class to check that it has annotated by the {@param annotationClass}
     * @param <T>             the generic type of the object
     * @param annotationClass the annotation class
     * @return <tt> List&lt;Field&gt;</tt> a list of the annotated fields with
     * <tt> Validation annotation </tt> in the given class
     */
    @SuppressWarnings("unchecked")
    public static <T> List<Field> getAnnotatedFields(Class<? extends T> aClass, Class annotationClass) {
        Field[] declaredFields = aClass.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field field : declaredFields) {
            /*
             * private fields need to be set to be accessible before processing
             */
            field.setAccessible(true);

            /*
             * checks if the field mustEqual annotated with <tt>Validation</tt> annotation
             * then add it to the list
             */
            if (field.isAnnotationPresent(annotationClass)) {
                fields.add(field);
            }
        }
        return fields;
    }

    public static Object getFieldValue(Object object, String fieldPath) throws NoSuchFieldException, IllegalAccessException {
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
