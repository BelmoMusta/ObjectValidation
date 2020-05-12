package utils;

import io.github.belmomusta.validation.exception.ValidationException;
import io.github.belmomusta.validation.utils.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UtilsTest {

    @Test
    public void testArrayUtils() {
        List list = ArrayUtils.castArrayToList(new int[]{1, 2, 3, 7, 8});
        Assert.assertNotNull(list);
        Assert.assertEquals(list.size(), 5);
        Assert.assertEquals(list.get(0).getClass(), Integer.class);
        Assert.assertEquals(list.get(0), 1);

    }

    @Test
    public void testGetNormally() throws ValidationException {
        Object o = ArrayUtils.get(new int[]{1, 2, 3, 7, 8}, 2);
        Assert.assertEquals(o, 3);
        Assert.assertFalse(o.getClass() == int.class);
        Assert.assertTrue(o.getClass() == Integer.class);// make sur the boxed type is returned

    }

    @Test(expected = ValidationException.class)
    public void testGetWithNullArray() throws ValidationException {
        Object o = ArrayUtils.get(null, 3);
        Assert.assertNull(o);

    }

}
