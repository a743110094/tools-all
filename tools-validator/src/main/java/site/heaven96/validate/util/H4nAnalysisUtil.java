package site.heaven96.validate.util;

import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.lang.handler.operator.*;

import javax.validation.constraints.NotNull;

/**
 * 超级分析工具
 *
 * @author Heaven96
 * @date 2021/10/20
 */
public class H4nAnalysisUtil {

    private static AbstractFixedValueHandler getLogic() {
        //非空
        AbstractFixedValueHandler handler1 = new NotNullFixedValueHandler();
        //空
        AbstractFixedValueHandler handler2 = new NullFixedValueHandler();
        //无有效字符
        AbstractFixedValueHandler handler3 = new HasNoTextFixedValueHandler();
        //含有效字符
        AbstractFixedValueHandler handler4 = new HasTextFixedValueHandler();
        //介于之间  仅限数字 日期比较
        AbstractFixedValueHandler handler6 = new AbstractBetweenAndFixedValueHandler() {
            @Override
            public boolean subHandle(Object obj, Operator operator, @NotNull Object[] valueSet) {
                return this.handle(obj, operator, valueSet);
            }
        };
        //对象相等
        AbstractFixedValueHandler handler7 = new AbstractEqualsFixedValueHandler() {
            @Override
            public boolean subHandle(Object obj, Operator operator, Object standardVal) {
                return this.handle(obj, operator, new Object[]{standardVal});
            }
        };


        handler1.setNext(handler2);
        handler2.setNext(handler3);
        handler3.setNext(handler4);
        handler4.setNext(handler6);
        handler6.setNext(handler7);
        return handler1;
    }

    public static boolean analysis(Object obj, Operator operator, @NotNull String[] valueSet){
       return getLogic().handle(obj, operator, valueSet);
    }

}
