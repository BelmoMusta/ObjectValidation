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
        String[] split = fieldPath.split("\\.");
        Object next = object;

        if (next != null) {
            for (String fieldName : split) {
                Class cls = next.getClass();
                Field field = cls.getDeclaredField(fieldName);
                field.setAccessible(true);
                next = field.get(next);
            }
        }
        return next;
    }

}
