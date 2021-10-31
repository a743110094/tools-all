package site.heaven96.validate.common.annotation;

import org.springframework.core.annotation.Order;
import site.heaven96.validate.common.enums.Condition;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * H4nCheck 配合 H4nUnionCheck 使用
 *
 * @author Heaven96
 * @date 2021/10/05
 */
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(H4nCheck.List.class)
@Order(99999)
public @interface H4nCheck{

    /**
     * 忽略大小写
     *
     * @return boolean
     */
    boolean ignoreCase() default true;

    /**
     * 逻辑
     *
     * @return {@code Condition}
     */
    Condition logic() default Condition.NONE;

    /**
     * SPEL表达式
     *
     * @return {@code String}
     */
    String spel();

    /**
     * 序号 对应sql中的参数序号 最小为1
     *
     * @return int
     */
    int group() default 1;

    /**
     * 运算符
     * 表述字段值和valueSet之间的逻辑关系
     *
     * @return {@code Logic}
     */
    Logic operator() default Logic.AUTO;

    /**
     * 值集来源
     *
     * @return {@code LegalOrigin}
     */
    LegalOrigin origin() default LegalOrigin.AUTO;

    /**
     * 合法值（集）
     *
     * @return {@code String[]}
     */
    String[] legal() default {};

    /**
     * 属性注释 字段的含义 用于打印日志
     *
     * @return {@code String}
     */
    String fieldNote() default "";

    @Target({FIELD})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        H4nCheck[] value();
    }

}
