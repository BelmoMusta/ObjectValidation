package utils;

import musta.belmo.validation.utils.ArrayUtils;
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

    } @Test
    public void testGet() {
        List list = ArrayUtils.castArrayToList(new int[]{1, 2, 3, 7, 8});

        Object o = ArrayUtils.get(new int[]{1, 2, 3, 7, 8}, 2);

    }

}
