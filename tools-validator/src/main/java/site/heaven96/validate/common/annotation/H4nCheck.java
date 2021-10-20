package site.heaven96.validate.common.annotation;

import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.common.enums.ValueSetOrigin;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * H4nCheck 配合 H4nUnionCheck 使用
 *
 * @author lgw3488
 * @date 2021/10/05
 */
@Target({FIELD})
@Retention(RUNTIME)
@Repeatable(H4nCheck.List.class)
public @interface H4nCheck {


    /**
     * 逻辑
     *
     * @return {@code Logic}
     */
    Logic logic() default Logic.NONE;

    /**
     * 字段
     *
     * @return {@code String}
     */
    String field();

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
     * @return {@code Operator}
     */
    Operator operator() default Operator.AUTO;

    /**
     * 值集来源
     *
     * @ValueSetOrigin.FIXED_VALUE //TODO 基于此分路判断 可以考虑淘汰此字段  {} 的是固定值  #{}是取别的属性值  SELECT 开头为sql
     */
    ValueSetOrigin valueSetOrigin() default ValueSetOrigin.AUTO;

    /**
     * 值集
     *
     * @return {@code String[]}
     */
    String[] valueSet() default {};

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
