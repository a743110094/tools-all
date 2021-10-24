package site.heaven96.example;

import cn.hutool.core.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import site.heaven96.validate.util.H4nCompareUtil;

import java.math.BigDecimal;
import java.util.Date;


@RunWith(BlockJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class CompareUtilTests {

    @Test
    public void SuperCompareUtilTest() {

        //////////////////////////////////////////数字
        BigDecimal a = new BigDecimal("40");
        String b = "40.00";
        boolean equals = H4nCompareUtil.equals(a, b);
        Assert.isTrue(equals);

        String c = "40";
        String d = "40.00";
        boolean isException = false;
        try {
            boolean equals2 = H4nCompareUtil.lessOrEquals(c, d);
        }catch (Exception e){
            isException = true;
        }
        Assert.isTrue(isException);
        int c1 = 40;
        String d2 = "40.00";
        boolean equals3 = H4nCompareUtil.greaterOrEquals(c1, d2);
        Assert.isTrue(equals3);

        BigDecimal dfd222 = new BigDecimal("39.999999999999999999")  ;
        String dfd2222 = "40";
        Assert.isTrue(H4nCompareUtil.isLess(dfd222,dfd2222));
        Assert.isTrue(H4nCompareUtil.lessOrEquals(dfd222,dfd2222));



        //////////////////////////////////////////字符串
        String dfd = "sdsd";
        String dfd2 = "SDSD";
        Assert.isTrue(H4nCompareUtil.equalsIgnoreCase(dfd,dfd2));
        Assert.isFalse(H4nCompareUtil.equals(dfd,dfd2));

        //////////////////////////////////////////日期
        //等于
        Date now = new Date();
        Date date0 =  new Date(0);//1970年xxxxxxxxx
        Date before =  new Date(1000);//1970年xxxxxxxxx
        Assert.isFalse(H4nCompareUtil.equals(now,before));
        Assert.isFalse(H4nCompareUtil.equalsIgnoreCase(now,before));
        //晚于
        Assert.isTrue(H4nCompareUtil.lessOrEquals(before,now));
        Assert.isTrue(H4nCompareUtil.isLess(before,now));
        Assert.isFalse(H4nCompareUtil.isLess(null,now));
        Assert.isFalse(H4nCompareUtil.lessOrEquals(null,now));
        Assert.isFalse(H4nCompareUtil.isLess(null,null));
        Assert.isFalse(H4nCompareUtil.isGreater(now,now));

        //早于
        Assert.isTrue(H4nCompareUtil.isGreater(now,before));
        Assert.isTrue(H4nCompareUtil.greaterOrEquals(now,before));
        Assert.isFalse(H4nCompareUtil.isGreater(null,before));
        Assert.isFalse(H4nCompareUtil.greaterOrEquals(null,before));
        Assert.isFalse(H4nCompareUtil.isGreater(before,before));

        //介于
        Assert.isTrue(H4nCompareUtil.greaterOrEquals(before,date0) && H4nCompareUtil.greaterOrEquals(now,before));


        //期望的报错
        boolean flag = false;
        try {
            H4nCompareUtil.isLess(now,null);
        }catch (Exception e){
            flag = true;
        }
        Assert.isTrue(flag);

    }


}

