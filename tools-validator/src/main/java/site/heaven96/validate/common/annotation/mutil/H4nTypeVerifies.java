package site.heaven96.validate.common.annotation.mutil;

import site.heaven96.validate.common.annotation.H4nTypeCheck;
import site.heaven96.validate.common.validtor.H3cTypeValidtor;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * H3C数据校验
 *
 * @author Heaven96
 * @date 2021/09/30
 */
@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = H3cTypeValidtor.class)
public @interface H4nTypeVerifies {
    H4nTypeCheck[] value();
}
