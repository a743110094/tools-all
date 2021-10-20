package site.heaven96.validate.common.annotation;

import site.heaven96.validate.common.annotation.mutil.H4nTbChecks;
import site.heaven96.validate.common.enums.TbCheck;
import site.heaven96.validate.common.validtor.H3cTbCheckValidtor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 表约束验证
 *
 * @author lgw3488
 * @date 2021/09/30
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = H3cTbCheckValidtor.class)
@Repeatable(H4nTbChecks.class)
public @interface H4nTbCheck {

    /**
     * 约束
     *
     * @return {@code TbCheck}
     */
    TbCheck check();

    /**
     * 模式
     *
     * @return {@code String}
     */
    @Deprecated
    String schema() default "";

    /**
     * 表名
     *
     * @return {@code String}
     */
    @Deprecated
    String tableName();

    /**
     * Bean属性名称 默认指定这个 构造器会自动转下划线
     *
     * @return {@code String}
     */
    String[] propertyNames();

    /**
     * 追加SQL   用户自定义的sql，追加在拼接好的sql之后
     *
     * @return {@code String}
     */
    String appendSql() default "";


    /**
     * 实字段名
     *
     * @return {@code String[]}
     */
    String[] realFieldName() default {};


    /**
     * 错误消息
     *
     * @return {@code String}
     */
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
