package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Operator;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * 数字等于处理程序
 *
 * @author Heaven96
 * @date 2021/10/20
 */
public class NumberEqualsFixedValueHandler extends AbstractEqualsFixedValueHandler {

    @Override
    public boolean subHandle(Object obj, Operator operator,Object standardVal) {
        boolean standardValIsNumber = NumberUtil.isNumber(StrUtil.str(standardVal,StandardCharsets.UTF_8));
        if (!standardValIsNumber) {
            //下一个处理器
            AssertUtil.isTrueThrowBeforeExp(nextEqualsHandler()!=null,AE_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextEqualsHandler().subHandle(obj, operator, standardVal);
        }
        //标准值
        boolean standardValueIsNumber = obj instanceof Number;
        if (!standardValueIsNumber) {
            //下一个处理器
            AssertUtil.isTrueThrowBeforeExp(nextEqualsHandler()!=null,AE_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextEqualsHandler().subHandle(obj, operator, standardVal);
        }
        return NumberUtil.equals(new BigDecimal(StrUtil.str(obj, StandardCharsets.UTF_8)),new BigDecimal(StrUtil.str(standardVal,StandardCharsets.UTF_8)));
    }

}
