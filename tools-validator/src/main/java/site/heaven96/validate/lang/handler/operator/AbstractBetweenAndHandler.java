package site.heaven96.validate.lang.handler.operator;

import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.util.AssertUtil;

import javax.validation.constraints.NotNull;

public abstract class AbstractBetweenAndHandler extends AbstractFixedValueAbstractHandler {
    protected static final String BA_HANDLER_NOT_MATCHES_ERR_MSG = "\n===> 指定Operator为BETWEEN_AND时，只能针对数字或者日期类进行比较，没有匹配到处理器";

    private AbstractBetweenAndHandler nextAbstractBetweenAndHandler;

    public AbstractBetweenAndHandler nextBetweenAndHandler() {
        return this.nextAbstractBetweenAndHandler;
    }

    public void setNextBetweenAndHandler(AbstractBetweenAndHandler next) {
        this.nextAbstractBetweenAndHandler = next;
    }

    private boolean toHandler(Object obj, Operator operator, @NotNull String[] valueSet){
        AbstractBetweenAndHandler handler1 = new NumberBetweenAndHandler();
        AbstractBetweenAndHandler handler2 = new DateBetweenAndHandler();
        handler1.setNextBetweenAndHandler(handler2);
        return handler1.subHandle(obj, operator, valueSet);
    }

    public abstract boolean subHandle(Object obj, Operator operator, @NotNull String[] valueSet);

    /**
     * 处理请求
     *
     * @param obj      OBJ
     * @param operator 运算符
     * @param valueSet 值集
     * @return boolean
     */
    @Override
    public boolean handle(Object obj, Operator operator, @NotNull String[] valueSet){
        if (Operator.BETWEEN_AND != operator) {
            AssertUtil.isTrueThrowH4nBeforeValidateCheckException(getNext()!=null,FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, operator, valueSet);
        }
        return toHandler(obj, operator, valueSet);
    }
}
