package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.ObjectUtil;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Operator;

import javax.validation.constraints.NotNull;

public class NullFixedValueHandler extends AbstractFixedValueHandler {
    /**
     * 处理请求
     *
     * @param obj      OBJ
     * @param operator 运算符
     * @param valueSet 值集
     * @return boolean
     */
    @Override
    public boolean handle(Object obj, Operator operator, @NotNull Object[] valueSet) {
        if (Operator.IS_NULL != operator) {
            AssertUtil.isTrueThrowBeforeExp(getNext()!=null,FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, operator, valueSet);
        }
        return ObjectUtil.isNull(obj);
    }
}
