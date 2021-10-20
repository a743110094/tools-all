package site.heaven96.validate.common.annotation.mutil;


import site.heaven96.validate.common.annotation.H4nUnionFieldVerify;

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
public @interface H4nFieldRequireVerifies {
    H4nUnionFieldVerify[] value();
}
