package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.util.AssertUtil;
import site.heaven96.validate.util.H4nCompareUtil;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

/**
 * 数字 介于 处理程序
 *
 * @author lgw3488
 * @date 2021/10/19
 */
public class NumberBetweenAndHandler extends AbstractBetweenAndHandler {
    /**
     * 右间隔符号无效错误消息
     */
    private static final String NB_RIGHT_INTERVAL_SYMBOL_IS_INVALID_ERR_MSG = "\n===>调用数字 介于 处理程序时，左区间标识符必须是)或者\\[，您提供的是【{}】";
    /**
     * 左间隔符号无效错误消息
     */
    private static final String NB_LEFT_INTERVAL_SYMBOL_IS_INVALID_ERR_MSG = "\n===>调用数字 介于 处理程序时，左区间标识符必须是)或者\\[，您提供的是【{}】";
    /**
     * 中间间隔符号不是错误消息
     */
    private static final String NB_MIDDLE_INTERVAL_SYMBOL_IS_NOT_COMMA_ERR_MSG = "\n===>调用数字 介于 处理程序时，间隔标识符必须是, ";
    /**
     * 区间表达式错误消息
     */
    private static final String NB_INTERVAL_EXPRESSION_ERR_MSG = "\n===>调用数字 介于 处理程序时，区间表达式长度  有误，示例：[1,2)，您提供的是【{}】";

    @Override
    public boolean subHandle(Object obj, Operator operator, @NotNull String[] valueSet){
        boolean objIsNumber = NumberUtil.isNumber(StrUtil.str(obj, StandardCharsets.UTF_8));
        if (!objIsNumber) {
            AssertUtil.isTrueThrowH4nBeforeValidateCheckException(nextBetweenAndHandler()!=null,BA_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextBetweenAndHandler().subHandle(obj, operator, valueSet);
        }
        final String exp = ArrayUtil.firstNonNull(valueSet).trim();//like [1,2)
        AssertUtil.isTrueThrowH4nBeforeValidateCheckException(exp.length() > 3, StrUtil.format(NB_INTERVAL_EXPRESSION_ERR_MSG));
        char lExp = exp.charAt(0);
        AssertUtil.isTrueThrowH4nBeforeValidateCheckException((lExp == 40 || lExp == 91), StrUtil.format(NB_LEFT_INTERVAL_SYMBOL_IS_INVALID_ERR_MSG, lExp));
        char rExp = exp.charAt(NumberUtil.max(0, (exp.length() - 1)));
        AssertUtil.isTrueThrowH4nBeforeValidateCheckException((rExp == 41 || rExp == 93), StrUtil.format(NB_RIGHT_INTERVAL_SYMBOL_IS_INVALID_ERR_MSG, rExp));
        int commaIndex = exp.indexOf(',');
        AssertUtil.isFalseThrowH4nBeforeValidateCheckException(commaIndex == -1, NB_MIDDLE_INTERVAL_SYMBOL_IS_NOT_COMMA_ERR_MSG);
        final String minVal = exp.substring(1, commaIndex);
        final String maxVal = exp.substring(commaIndex + 1, exp.length() - 1);
        if (H4nCompareUtil.isLess(obj, minVal) || H4nCompareUtil.isGreater(obj, maxVal)) {
            //比最小值小 比最大值大
            return false;
        }
        //排除开区间 踩在区间上
        return (H4nCompareUtil.notEquals(obj, minVal) || lExp == 91) && (H4nCompareUtil.notEquals(obj, maxVal) || rExp == 93);
    }
}
