package site.heaven96.validate.lang.handler.base.compare;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.assertes.util.AssertUtil;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * 数字比较处理程序
 *
 * @author Heaven96
 * @date 2021/10/19
 */
public class NumberCompare extends AbCompare {
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
        boolean obj1IsNumber = obj1 instanceof Number;
        if (obj1IsNumber) {
            final String objStr1 = StrUtil.str(obj1, StandardCharsets.UTF_8);
            final String objStr2 = StrUtil.str(obj2, StandardCharsets.UTF_8);
            boolean obj2IsNumber = NumberUtil.isNumber(objStr2);
            AssertUtil.isTrueThrowBeforeExp(obj2IsNumber, "\n==>进行数字比较时，值域中的[{}]不能被转换为数字，无法比较", objStr2);
            BigDecimal num1 = new BigDecimal(objStr1);
            BigDecimal num2 = new BigDecimal(objStr2);
            return NumberUtil.equals(num1, num2) ? 0 : NumberUtil.isGreater(num1, num2) ? 1 : -1;
        } else {
            //不是日期类的比较 传递给责任链的下一环
            AssertUtil.isTrueThrowBeforeExp(ObjectUtil.isNotNull(getNext()), FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj1, obj2, ignoreCase);
        }
    }
}
