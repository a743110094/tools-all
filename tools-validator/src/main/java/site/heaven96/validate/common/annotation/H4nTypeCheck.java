package site.heaven96.validate.common.annotation;

import site.heaven96.validate.common.annotation.mutil.H4nTypeVerifies;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.common.enums.Relation;
import site.heaven96.validate.common.enums.TypeCheckRule;
import site.heaven96.validate.common.validtor.H3cTypeValidtor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * H3C数据校验
 *
 * @author lgw3488
 * @date 2021/09/30
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = H3cTypeValidtor.class)
@Repeatable(H4nTypeVerifies.class)
public @interface H4nTypeCheck {
    /**
     * 批次ID
     *
     * @return {@code String}
     */
    TypeCheckRule rule();

    /**
     * 字段名称
     *
     * @return {@code String}
     */
    String[] fieldNames() default {};

    /**
     * 字段名称A
     *
     * @return {@code String[]}
     */
    String[] fieldNamesA() default {};

    /**
     * 字段名称B
     *
     * @return {@code String[]}
     */
    String[] fieldNamesB() default {};

    /**
     * AB关系 默认一对一 OTO
     *
     * @return {@code String}
     */
    Relation relationshipA2B() default Relation.ONE_TO_ONE;


    /**
     * SQL 备用
     *
     * @return {@code String}
     */
    String sql() default "SELECT 1 FROM DUAL";

    /**
     * 运算符
     *
     * @return {@code Operator}
     */
    Operator operator() default Operator.EQUALS;

    /**
     * 结果集
     *
     * @return {@code String[]}
     */
    String[] resultSet() default {};

    /**
     * 错误消息
     *
     * @return {@code String}
     */
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
