package musta.belmo.validation.utils;

import java.lang.reflect.Field;

public class ReflectUtils {

    public static Object getValueFromField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {

        Field declaredField = object.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        return declaredField.get(object);
    }


}
