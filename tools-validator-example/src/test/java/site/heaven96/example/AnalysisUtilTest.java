package site.heaven96.example;

import cn.hutool.core.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.util.H4nAnalysisUtil;

import java.math.BigDecimal;

@RunWith(BlockJUnit4ClassRunner.class)
public class AnalysisUtilTest {
    @Test
    public void logicTest() {

        Assert.isFalse(H4nAnalysisUtil.analysis("a", Logic.IS_NULL, null));
        Assert.isTrue(H4nAnalysisUtil.analysis("a", Logic.NOT_NULL, null));
        Assert.isFalse(H4nAnalysisUtil.analysis("a", Logic.HAS_NO_TEXT, null));
        Assert.isTrue(H4nAnalysisUtil.analysis("a", Logic.HAS_TEXT, null));

        Assert.isFalse(H4nAnalysisUtil.analysis("", Logic.IS_NULL, null));
        Assert.isTrue(H4nAnalysisUtil.analysis("", Logic.NOT_NULL, null));
        Assert.isTrue(H4nAnalysisUtil.analysis("", Logic.HAS_NO_TEXT, null));
        Assert.isFalse(H4nAnalysisUtil.analysis("", Logic.HAS_TEXT, null));

        Assert.isTrue(H4nAnalysisUtil.analysis(null, Logic.IS_NULL, null));
        Assert.isFalse(H4nAnalysisUtil.analysis(null, Logic.NOT_NULL, null));
        Assert.isTrue(H4nAnalysisUtil.analysis(null, Logic.HAS_NO_TEXT, null));
        Assert.isFalse(H4nAnalysisUtil.analysis(null, Logic.HAS_TEXT, null));

        Assert.isTrue(H4nAnalysisUtil.analysis("3", Logic.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Logic.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.0000000000000000", Logic.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5.0000000000000000", Logic.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Logic.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Logic.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1", Logic.BETWEEN_AND, new String[]{"[1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5", Logic.BETWEEN_AND, new String[]{"[1,5]"}));


        Assert.isTrue(H4nAnalysisUtil.analysis("3", Logic.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Logic.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.0000000000000000", Logic.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.0000000000000000", Logic.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Logic.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Logic.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1", Logic.BETWEEN_AND, new String[]{"[1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5", Logic.BETWEEN_AND, new String[]{"[1,5)"}));

        Assert.isTrue(H4nAnalysisUtil.analysis("3", Logic.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Logic.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1.0000000000000000", Logic.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.0000000000000000", Logic.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Logic.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Logic.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1", Logic.BETWEEN_AND, new String[]{"(1,5)"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5", Logic.BETWEEN_AND, new String[]{"(1,5)"}));


        Assert.isTrue(H4nAnalysisUtil.analysis("3", Logic.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("1.00000000000000001", Logic.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1.0000000000000000", Logic.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5.0000000000000000", Logic.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("0.9999999999999", Logic.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("5.000000000000000000000001", Logic.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isFalse(H4nAnalysisUtil.analysis("1", Logic.BETWEEN_AND, new String[]{"(1,5]"}));
        Assert.isTrue(H4nAnalysisUtil.analysis("5", Logic.BETWEEN_AND, new String[]{"(1,5]"}));

        Assert.isTrue(H4nAnalysisUtil.analysis(new BigDecimal("5"), Logic.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(new BigDecimal("5.000000"), Logic.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5, Logic.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5.0, Logic.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5L, Logic.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis((double) 5, Logic.EQUALS, new String[]{"5"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5, Logic.EQUALS, new String[]{"5.0"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5.0, Logic.EQUALS, new String[]{"5.00"}));
        Assert.isTrue(H4nAnalysisUtil.analysis(5L, Logic.EQUALS, new String[]{"5.0"}));
        Assert.isTrue(H4nAnalysisUtil.analysis((double) 5, Logic.EQUALS, new String[]{"5.0"}));

        //完善测试用例 TODO
    }
}
