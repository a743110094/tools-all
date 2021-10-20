package site.heaven96.validate.util;

import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.lang.handler.operator.*;

import javax.validation.constraints.NotNull;

/**
 * 超级分析工具
 *
 * @author lgw3488
 * @date 2021/10/20
 */
public class H4nAnalysisUtil {

    private static AbstractFixedValueAbstractHandler getLogic() {
        AbstractFixedValueAbstractHandler handler1 = new NotNullHandler();
        AbstractFixedValueAbstractHandler handler2 = new NullHandler();
        AbstractFixedValueAbstractHandler handler3 = new HasNoTextHandler();
        AbstractFixedValueAbstractHandler handler4 = new HasTextHandler();
        AbstractFixedValueAbstractHandler handler5 = new HasNoTextHandler();
        AbstractFixedValueAbstractHandler handler6 = new AbstractEqualsHandler() {
            @Override
            public boolean subHandle(Object obj, Operator operator, String standardVal) {
                return this.handle(obj, operator, new String[]{standardVal});
            }
        };
        AbstractFixedValueAbstractHandler handler7 = new AbstractBetweenAndHandler() {
            @Override
            public boolean subHandle(Object obj, Operator operator, @NotNull String[] valueSet) {
                return this.handle(obj, operator, valueSet);
            }
        };

        handler1.setNext(handler2);
        handler2.setNext(handler3);
        handler3.setNext(handler4);
        handler4.setNext(handler5);
        handler5.setNext(handler6);
        handler6.setNext(handler7);
        return handler1;
    }

    public static boolean analysis(Object obj, Operator operator, @NotNull String[] valueSet) throws Exception {
       return getLogic().handle(obj, operator, valueSet);
    }

}
