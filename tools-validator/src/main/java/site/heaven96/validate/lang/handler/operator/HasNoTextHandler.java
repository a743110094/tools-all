package site.heaven96.validate.lang.handler.operator;

import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.util.AssertUtil;

import javax.validation.constraints.NotNull;

/**
 * 非空处理程序
 *
 * @author lgw3488
 * @date 2021/10/20
 */
public class HasNoTextHandler extends AbstractFixedValueAbstractHandler{
    /**
     * 处理请求
     *
     * @param obj      OBJ
     * @param operator 运算符
     * @param valueSet 值集
     * @return boolean
     */
    @Override
    public boolean handle(Object obj, Operator operator, @NotNull String[] valueSet) {
        if (Operator.HAS_NO_TEXT != operator) {
            AssertUtil.isTrueThrowH4nBeforeValidateCheckException(getNext()!=null,FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, operator, valueSet);
        }
        AssertUtil.isTrueThrowH4nBeforeValidateCheckException(getNext() instanceof HasTextHandler,HAS_NO_TEXT_MUST_NEXT_TO_HAS_TEXT);
        operator = Operator.HAS_TEXT;
        return !getNext().handle(obj, operator, valueSet);
    }
}