package site.heaven96.validate.lang.handler.operator;

import site.heaven96.validate.common.enums.Operator;

import javax.validation.constraints.NotNull;

/**
 * 固定值抽象处理程序
 * 、 *
 *
 * @author lgw3488
 * @date 2021/10/18
 */
public abstract class AbstractFixedValueAbstractHandler {
    protected static final String FCV_NO_MATCHED_HANDLER_ERR_MSG = "\n===>没有匹配到值集比较处理器";

    protected static final String NOT_NULL_MUST_NEXT_TO_NULL = "===>构造责任链错误，NOT_NULL处理器之后必须是NULL处理器";

    protected static final String HAS_NO_TEXT_MUST_NEXT_TO_HAS_TEXT = "===>构造责任链错误，HAS_NO_TEXT处理器之后必须是HAS_TEXT处理器";

    protected static final String NOT_EQUALS_MUST_NEXT_TO_EQUALS = "===>构造责任链错误，NOT_EQUALS处理器之后必须是EQUALS处理器";


    private AbstractFixedValueAbstractHandler next;

    public AbstractFixedValueAbstractHandler getNext() {
        return next;
    }

    public void setNext(AbstractFixedValueAbstractHandler next) {
        this.next = next;
    }

    /**
     * 处理请求
     *
     * @param valueSet 值集
     * @param obj      OBJ
     * @param operator 运算符
     * @return boolean
     */
    public abstract boolean handle(Object obj, @NotNull final Operator operator, @NotNull final String[] valueSet);
}
