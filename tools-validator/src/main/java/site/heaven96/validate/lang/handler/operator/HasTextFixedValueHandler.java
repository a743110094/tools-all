package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.StrUtil;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Logic;

import javax.validation.constraints.NotNull;

/**
 * 具有文本处理程序
 *
 * @author Heaven96
 * @date 2021/10/20
 */
public class HasTextFixedValueHandler extends AbstractFixedValueHandler {
    /**
     * 处理请求
     *
     * @param obj      OBJ
     * @param logic    运算符
     * @param valueSet 值集
     * @return boolean
     */
    @Override
    public boolean handle(Object obj, Logic logic, @NotNull Object[] valueSet) {
        if (Logic.HAS_TEXT != logic) {
            AssertUtil.isTrueThrowBeforeExp(getNext() != null, FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, logic, valueSet);
        }
        return StrUtil.isNotBlank(StrUtil.utf8Str(obj));
    }

}
