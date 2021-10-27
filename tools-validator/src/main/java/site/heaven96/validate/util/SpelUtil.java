package site.heaven96.validate.util;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
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

    private static final Cache<String, Object> cache = CacheUtil.newLFUCache(50, 30 * 60 * 1000);

    /**
     * 到达
     * <p>
     * 注意：
     * #this.employees[all].name
     * #this.employees[0].name
     * 的区别
     *
     * @param expression 表达式
     * @param obj        OBJ
     * @return {@code Object}
     */
    public static Object get(@NotNull String expression, Object obj) {
        if (obj instanceof Serializable) {
            Object cache = getCache(expression, (Serializable) obj);
            if (ObjectUtil.isNotNull(cache)) {
                return cache;
            }
        }
        /*表达式要求返回集合*/
        int index = expression.indexOf(COLL_SPEL_MARK);
        boolean isColl = index != -1;
        // boolean isColl = expression.contains(COLL_SPEL_MARK);
        Object retObj = null;
//        if (!isColl) {
//            return parse(expression, obj);
//        }
        if (!isColl) {
            retObj = parse(expression, obj);
        }
        //集合 先取出集合 再取集合的属性
        /* 截取[all] 之前的*/
        //  retObj = parse(StrUtil.subBefore(expression, COLL_SPEL_MARK,true), obj);
        if (isColl) {

            retObj = parse(expression.substring(0, index), obj);
            retObj = CollUtil.getFieldValues((Collection) retObj, expression.substring(index + COLL_SPEL_MARK_LEN));
        }
        if (obj instanceof Serializable) {
            cache.put(new StringBuilder(expression).append(obj.hashCode()).toString(), retObj);
        }
        return retObj;
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

    /**
     * 读PUT缓存
     * 读写缓存
     * 尝试读  读到返回对象  没读到返回 null
     *
     * @param expression 表达式
     * @param obj        对象
     * @return boolean
     */
    private static Object getCache(String expression, Serializable obj) {
        return cache.get(new StringBuilder(expression).append(obj.hashCode()).toString());
    }
}
