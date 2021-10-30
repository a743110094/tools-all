package site.heaven96.validate.common.annotation;

import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.common.validtor.H4nUnionCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * H4N多字段联合检查
 * 思路：某个字段收到约束 分为定值约束，sql结果集约束和动态传值约束三种
 * 定值约束{@ValueSetOrigin.FIXED_VALUE} 此字段的值必须在resultSet中
 * 定值约束{@ValueSetOrigin.DYNAMIC_SPECIFIED_VALUE} 此字段的值必须在refRetSetFieldName指定的Java属性名对应的中
 * 定值约束{@ValueSetOrigin.SQL_RESULTS} 此字段的值必须在SQL（静态/动态）执行的结果中
 *
 * @author Heaven96
 * @date 2021/10/05
 */
@Documented
@Target({TYPE, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = H4nUnionCheckValidator.class)
@Repeatable(H4nUnionCheck.List.class)
public @interface H4nUnionCheck {
    /**
     * 组号
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
    Logic operator() default Logic.EQUALS;

    /**
     * 值集来源
     *
     * @ValueSetOrigin.FIXED_VALUE //TODO 基于此分路判断
     */
    LegalOrigin valueSetOrigin() default LegalOrigin.AUTO;

    /**
     * 值集
     *
     * @return {@code String[]}
     */
    String[] valueSet() default {};

    /**
     * SQL 备用
     *
     * @return {@code String}
     */
    String sql() default "";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        H4nUnionCheck[] value();
    }
}
