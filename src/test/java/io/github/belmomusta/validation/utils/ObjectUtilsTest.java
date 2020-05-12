package io.github.belmomusta.validation.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ObjectUtilsTest {


    @Test
    public void testGetOrDefault() {
        String getorDefault = ObjectUtils.getOrDefault("all", "another");
        String nullGet = ObjectUtils.getOrDefault(null, "another");
        String nullDefault = ObjectUtils.getOrDefault("all", null);
        String effectivNull = ObjectUtils.getOrDefault(null, null);
        assertEquals("all", getorDefault);
        assertEquals("all", nullDefault);
        assertEquals("another", nullGet);
        assertNull(effectivNull);

    }

    @Test
    public void testGetIf() {
        String getIfTrue = ObjectUtils.getIf(true, "all");
        String getIfFalse = ObjectUtils.getIf(false, "all");
        assertEquals("all", getIfTrue);
        assertNull(getIfFalse);

    }
}