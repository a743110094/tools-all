package site.heaven96.example;

import cn.hutool.core.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.util.H4nAnalysisUtil;

import java.math.BigDecimal;

@RunWith(BlockJUnit4ClassRunner.class)
public class AnalysisUtilTest {
    @Test
    public void logicTest(){

        Assert.isFalse(H4nAnalysisUtil.analysis("a", Operator.IS_NULL,null));
        Assert.isTrue(H4nAnalysisUtil.analysis("a", Operator.NOT_NULL,null));
        Assert.isFalse(H4nAnalysisUtil.analysis("a", Operator.HAS_NO_TEXT,null));
        Assert.isTrue(H4nAnalysisUtil.analysis("a", Operator.HAS_TEXT,null));

        Assert.isFalse(H4nAnalysisUtil.analysis("", Operator.IS_NULL,null));
        Assert.isTrue(H4nAnalysisUtil.analysis("", Operator.NOT_NULL,null));
        Assert.isTrue(H4nAnalysisUtil.analysis("", Operator.HAS_NO_TEXT,null));
        Assert.isFalse(H4nAnalysisUtil.analysis("", Operator.HAS_TEXT,null));

        Assert.isTrue(H4nAnalysisUtil.analysis(null, Operator.IS_NULL,null));
        Assert.isFalse(H4nAnalysisUtil.analysis(null, Operator.NOT_NULL,null));
        Assert.isTrue(H4nAnalysisUtil.analysis(null, Operator.HAS_NO_TEXT,null));
        Assert.isFalse(H4nAnalysisUtil.analysis(null, Operator.HAS_TEXT,null));

        Assert.isTrue(H4nAnalysisUtil.analysis("3", Operator.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Operator.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.0000000000000000", Operator.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5.0000000000000000", Operator.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Operator.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Operator.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1", Operator.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5", Operator.BETWEEN_AND, new String[]{"[1,5]"}));


        Assert.isTrue(H4nAnalysisUtil.analysis("3", Operator.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Operator.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.0000000000000000", Operator.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.0000000000000000", Operator.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Operator.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Operator.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1", Operator.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5", Operator.BETWEEN_AND, new String[]{"[1,5)"}));

        Assert.isTrue(H4nAnalysisUtil.analysis("3", Operator.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Operator.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1.0000000000000000", Operator.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.0000000000000000", Operator.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Operator.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Operator.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1", Operator.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5", Operator.BETWEEN_AND, new String[]{"(1,5)"}));


        Assert.isTrue(H4nAnalysisUtil.analysis("3", Operator.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Operator.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1.0000000000000000", Operator.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5.0000000000000000", Operator.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Operator.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Operator.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1", Operator.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5", Operator.BETWEEN_AND, new String[]{"(1,5]"}));

        Assert.isTrue(H4nAnalysisUtil.analysis(new BigDecimal("5"), Operator.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(new BigDecimal("5.000000"), Operator.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5, Operator.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5.0, Operator.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5L, Operator.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis((double) 5, Operator.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5, Operator.EQUALS, new String[]{"5.0"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5.0, Operator.EQUALS, new String[]{"5.00"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5L, Operator.EQUALS, new String[]{"5.0"}));
        Assert.isTrue(H4nAnalysisUtil.analysis((double) 5, Operator.EQUALS, new String[]{"5.0"}));

        //完善测试用例 TODO
    }
}
