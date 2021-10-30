package site.heaven96.validate.util;

import cn.hutool.core.collection.CollUtil;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * SPEL工具
 *
 * @author lgw3488
 * @date 2021/10/27
 */
public class SpelUtil {

    private static final String REGEX = "#{1}\\{(\\w*\\.*\\w*){1,3}}";
    private static final String COLL_SPEL_MARK = "[all].";
    private static final int COLL_SPEL_MARK_LEN = COLL_SPEL_MARK.length();
    private static final char POINT = '.';

    /**
     * 根据表达式获取值<br>
     *
     * <b>注意区别：</b><br>
     * <li>#this.employees[all].name 获取数组中<b>所有</b>职员的名字<br>
     * <li>#this.employees[0].name 获取数组中<b>第一个</b>职员的名字<br>
     *
     * @param expression 表达式
     * @param obj        OBJ
     * @return {@code Object}
     */
    public static Object get(@NotNull String expression, Object obj) {
        /*表达式要求返回集合*/
        int index = expression.indexOf(COLL_SPEL_MARK);
        boolean isColl = index != -1;
        if (isColl) {
            String collExp = expression.substring(0, index);
            String collPropertyExp = expression.substring(index + COLL_SPEL_MARK_LEN);
            //集合 先取出集合 再取集合的属性
            return CollUtil.getFieldValues((Collection) parse(collExp, obj), collPropertyExp);
        }
        return parse(expression, obj);
    }

    /**
     * 解析
     *
     * @param expression 表达式
     * @param obj        OBJ
     * @return {@code Object}
     */
    private static Object parse(String expression, Object obj) {
        //创建ExpressionParser解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        //SpEL表达式语法设置在parseExpression()入参内
        Expression exp = parser.parseExpression(expression);
        //执行SpEL表达式，执行的默认Spring容器是Spring本身的容器：ApplicationContext
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        //向容器内添加bean
        ctx.setRootObject(obj);
        //getValue有参数ctx，从新的容器中根据SpEL表达式获取所需的值
        Object result = exp.getValue(ctx, Object.class);
        return result;
    }

}
