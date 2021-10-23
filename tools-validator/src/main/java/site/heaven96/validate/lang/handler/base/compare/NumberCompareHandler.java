package site.heaven96.validate.lang.handler.base.compare;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.exception.H4nBeforeValidateCheckException;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * 数字比较处理程序
 *
 * @author Heaven96
 * @date 2021/10/19
 */
public class NumberCompareHandler extends AbstractCompareHandler {
    /**
     * 手柄
     * 处理请求
     * obj1 < obj2 返回 -1  obj1 = obj2 返回 0 obj1 > obj2返回 1
     *
     * @param obj1       obj1
     * @param obj2       obj2
     * @param ignoreCase 忽略大小写
     * @return boolean
     */
    @Override
    public int handle(Object obj1, Object obj2, boolean ignoreCase) {
        final String objStr1 = StrUtil.str(obj1, StandardCharsets.UTF_8);
        final String objStr2 = StrUtil.str(obj2, StandardCharsets.UTF_8);
        if (NumberUtil.isNumber(objStr1) && NumberUtil.isNumber(objStr2)) {
            BigDecimal bigDecimal1 = new BigDecimal(objStr1);
            BigDecimal bigDecimal2 = new BigDecimal(objStr2);
            if (NumberUtil.equals(bigDecimal1, bigDecimal2)) {
                return 0;
            }
            if (NumberUtil.isGreater(bigDecimal1, bigDecimal2)) {
                return 1;
            }
            return -1;
        } else if (getNext() != null) {
            return getNext().handle(obj1, obj2, ignoreCase);
        } else {
            throw new H4nBeforeValidateCheckException(FCV_NO_MATCHED_HANDLER_ERR_MSG);
        }
    }
}
