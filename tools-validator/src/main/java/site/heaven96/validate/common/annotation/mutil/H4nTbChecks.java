package site.heaven96.validate.common.annotation.mutil;


import site.heaven96.validate.common.annotation.H4nTbCheck;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 表约束验证
 *
 * @author Heaven96
 * @date 2021/09/30
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface H4nTbChecks {
    H4nTbCheck[] value();
}
