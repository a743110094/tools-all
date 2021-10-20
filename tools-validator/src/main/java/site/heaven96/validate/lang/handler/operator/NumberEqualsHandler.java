package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.util.AssertUtil;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * 数字等于处理程序
 *
 * @author lgw3488
 * @date 2021/10/20
 */
public class NumberEqualsHandler extends AbstractEqualsHandler{

    @Override
    public boolean subHandle(Object obj, Operator operator,String standardVal) {
        boolean standardValIsNumber = NumberUtil.isNumber(standardVal);
        if (!standardValIsNumber) {
            //下一个处理器
            AssertUtil.isTrueThrowH4nBeforeValidateCheckException(nextEqualsHandler()!=null,AE_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextEqualsHandler().subHandle(obj, operator, standardVal);
        }
        //标准值
        boolean standardValueIsNumber = obj instanceof Number;
        if (!standardValueIsNumber) {
            //下一个处理器
            AssertUtil.isTrueThrowH4nBeforeValidateCheckException(nextEqualsHandler()!=null,AE_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextEqualsHandler().subHandle(obj, operator, standardVal);
        }
        return NumberUtil.equals(new BigDecimal(StrUtil.str(obj, StandardCharsets.UTF_8)),new BigDecimal(standardVal));
    }
}
