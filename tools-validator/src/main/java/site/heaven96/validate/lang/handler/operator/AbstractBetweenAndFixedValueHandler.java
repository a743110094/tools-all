package site.heaven96.validate.lang.handler.operator;

import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Operator;

import javax.validation.constraints.NotNull;

public abstract class AbstractBetweenAndFixedValueHandler extends AbstractFixedValueHandler {
    protected static final String BA_HANDLER_NOT_MATCHES_ERR_MSG = "\n===> 指定Operator为BETWEEN_AND时，只能针对数字或者日期类进行比较，没有匹配到处理器";
    protected static final String VALUE_SET_IS_NULL_ERR_MSG = "\n===> 指定Operator为BETWEEN_AND时，合法值域为空";

    private AbstractBetweenAndFixedValueHandler nextAbstractBetweenAndHandler;

    public AbstractBetweenAndFixedValueHandler nextBetweenAndHandler() {
        return this.nextAbstractBetweenAndHandler;
    }

    public void setNextBetweenAndHandler(AbstractBetweenAndFixedValueHandler next) {
        this.nextAbstractBetweenAndHandler = next;
    }

    private boolean toHandler(Object obj, Operator operator, @NotNull Object[] valueSet){
        AbstractBetweenAndFixedValueHandler handler1 = new NumberBetweenAndFixedValueHandler();
        AbstractBetweenAndFixedValueHandler handler2 = new DateBetweenAndFixedValueHandler();
        handler1.setNextBetweenAndHandler(handler2);
        return handler1.subHandle(obj, operator, valueSet);
    }

    public abstract boolean subHandle(Object obj, Operator operator, @NotNull Object[] valueSet);

    /**
     * 处理请求
     *
     * @param obj      OBJ
     * @param operator 运算符
     * @param valueSet 值集
     * @return boolean
     */
    @Override
    public boolean handle(Object obj, Operator operator, @NotNull Object[] valueSet){
        if (Operator.BETWEEN_AND != operator) {
            AssertUtil.isTrueThrowBeforeExp(getNext()!=null,FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, operator, valueSet);
        }
        return toHandler(obj, operator, valueSet);
    }
}
