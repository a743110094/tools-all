package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.ArrayUtil;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.util.AssertUtil;

import javax.validation.constraints.NotNull;

/**
 * 等于处理程序
 *
 * @author lgw3488
 * @date 2021/10/18
 */
public abstract class AbstractEqualsHandler extends AbstractFixedValueAbstractHandler {

    protected static final String AE_HANDLER_NOT_MATCHES_ERR_MSG = "\n===> 指定Operator为Equals时，只能针对数字或者日期类进行比较，没有匹配到处理器";


    private AbstractEqualsHandler nextAbstractEqualsHandler;

    public AbstractEqualsHandler nextEqualsHandler() {
        return this.nextAbstractEqualsHandler;
    }

    public void setNextEqualsHandler(AbstractEqualsHandler next) {
        this.nextAbstractEqualsHandler = next;
    }

    private boolean toHandler(Object obj, Operator operator, @NotNull final String[] valueSet) {
        AbstractEqualsHandler handler1 = new NumberEqualsHandler();
        AbstractEqualsHandler handler2 = new DateEqualsHandler();
        handler1.setNextEqualsHandler(handler2);
        return handler1.subHandle(obj, operator, ArrayUtil.firstNonNull(valueSet));
    }

    public abstract boolean subHandle(Object obj, Operator operator, String standardVal);


    /**
     * 处理请求
     *
     * @param obj      OBJ
     * @param operator 运算符
     * @param valueSet 值集
     * @return boolean
     */
    @Override
    public boolean handle(Object obj, @NotNull final Operator operator, @NotNull final String[] valueSet) {
        if (Operator.EQUALS != operator) {
            AssertUtil.isTrueThrowH4nBeforeValidateCheckException(getNext()!=null,FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, operator, valueSet);
        }
        return toHandler(obj, operator,valueSet);
    }
}
