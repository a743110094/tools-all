package site.heaven96.example;

import cn.hutool.core.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import site.heaven96.validate.util.H4nCompareUtil;

import java.util.Date;


@RunWith(BlockJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CompareUtilTests {

    @Test
    public void SuperCompareUtilTest() {
        Object a = "40";
        String b = "40.00";
        boolean equals = H4nCompareUtil.equals(a, b);
        Assert.isTrue(equals);


        Object c = "40";
        String d = "40.00";
        boolean equals2 = H4nCompareUtil.lessOrEquals(c, d);
        Assert.isTrue(equals2);

        Object c1 = "40";
        String d2 = "40.00";
        boolean equals3 = H4nCompareUtil.greaterOrEquals(c1, d2);
        Assert.isTrue(equals3);

        String dfd = "sdsd";
        String dfd2 = "SDSD";
        Assert.isTrue(H4nCompareUtil.equalsIgnoreCase(dfd,dfd2));
        Assert.isFalse(H4nCompareUtil.equals(dfd,dfd2));
        Date dfd33 = new Date();
        Date dfd3 =  new Date(9999999);
        Assert.isFalse(H4nCompareUtil.equals(dfd33,dfd3));
        Assert.isFalse(H4nCompareUtil.equalsIgnoreCase(dfd33,dfd3));
        Assert.isTrue(H4nCompareUtil.lessOrEquals(dfd3,dfd33));

        String dfd222 = "39.999999999999999999";
        String dfd2222 = "40";
        Assert.isTrue(H4nCompareUtil.isLess(dfd222,dfd2222));
        Assert.isTrue(H4nCompareUtil.lessOrEquals(dfd222,dfd2222));
        Assert.isTrue(H4nCompareUtil.isGreater(dfd2222,dfd222));
        Assert.isTrue(H4nCompareUtil.greaterOrEquals(dfd2222,dfd222));
        Assert.isFalse(H4nCompareUtil.equals(dfd2222,dfd222));
        Assert.isFalse(H4nCompareUtil.equalsIgnoreCase(dfd2222,dfd222));
    }


}

