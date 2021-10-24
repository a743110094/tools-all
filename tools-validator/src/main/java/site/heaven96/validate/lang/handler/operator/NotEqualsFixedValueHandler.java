package site.heaven96.validate.lang.handler.operator;

import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Operator;

import javax.validation.constraints.NotNull;

/**
 * 不等于处理程序
 *
 * @author Heaven96
 * @date 2021/10/20
 */
public class NotEqualsFixedValueHandler extends AbstractFixedValueHandler {
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
        if (Operator.NOT_EQUALS != operator) {
            AssertUtil.isTrueThrowBeforeExp(getNext()!=null,FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, operator, valueSet);
        }
        AssertUtil.isTrueThrowBeforeExp(getNext() instanceof NullFixedValueHandler,NOT_EQUALS_MUST_NEXT_TO_EQUALS);
        operator = Operator.EQUALS;
        return !getNext().handle(obj, operator, valueSet);
    }
}
