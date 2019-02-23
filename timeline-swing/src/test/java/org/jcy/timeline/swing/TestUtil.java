package org.jcy.timeline.swing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class TestUtil {

    private static final Logger log = LoggerFactory.getLogger(TestUtil.class);

    public static Object findFieldValue(String name, Object target) {
        try {
            Field field = target.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(target);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Fail to find the field {} from class {}.", name, target.getClass().getName(), e);
        }
        return null;
    }
}
