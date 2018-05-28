package musta.belmo.validation.utils;

import musta.belmo.validation.annotation.Validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtils {

    public static Object getValueFromField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {

        Field declaredField = object.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        return declaredField.get(object);
    }

    /**
     * @param aClass          the class to check that it has annotated by the {@param annotationClass}
     * @param <T>             the generic type of the object
     * @param annotationClass the annotation class
     * @return <tt> List&lt;Field&gt;</tt> a list of the annotated fields with
     * <tt> Validation annotation </tt> in the given class
     */
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

}
