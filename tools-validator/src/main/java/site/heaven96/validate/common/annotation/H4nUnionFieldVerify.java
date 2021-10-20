package site.heaven96.validate.common.annotation;


import site.heaven96.validate.common.annotation.mutil.H4nFieldRequireVerifies;
import site.heaven96.validate.common.enums.RequireRule;
import site.heaven96.validate.common.enums.ValueSetOrigin;
import site.heaven96.validate.common.validtor.FieldRequireValidtor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 字段必输校验
 *
 * @author lgw3488
 * @date 2021/09/30
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FieldRequireValidtor.class)
@Repeatable(H4nFieldRequireVerifies.class)
public @interface H4nUnionFieldVerify {
    /**
     * 待验证字段
     *
     * @return {@code String}
     */
    String targetFeilds();

    /**
     * 参考字段
     *
     * @return {@code String[]}
     */
    String[] referenceFields() default {};

    /**
     * 规则
     *
     * @return {@code RequireRule}
     */
    RequireRule rule();

    /**
     * SQL
     */
    String sql() default "";

    /**
     * SQL参数集A
     */
    String[] sqlParaSetA() default {};

    /**
     * SQL参数集B
     */
    String[] sqlParaSetB() default {};

    /**
     * 值集来源
     * //TODO 基于此分路判断
     */
    ValueSetOrigin valueSetOrigin() default ValueSetOrigin.FIXED_VALUE;

    /**
     * 值集
     * 若参考字段符合某些值，则对目标字段验证 则配置这些值到值集
     *
     * @return {@code String[]}
     */
    String[] valueSet() default {};

    /**
     * 备注
     *
     * @return {@code String}
     */
    String note() default "";


    /**
     * 错误消息
     *
     * @return {@code String}
     */
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
