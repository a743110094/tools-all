package site.heaven96.validate.common.annotation;

import site.heaven96.validate.common.validtor.H4nReflectCheckValidator;
import site.heaven96.validate.common.validtor.H4nUnionCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
@Documented
@Target({TYPE, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = H4nReflectCheckValidator.class)
@Repeatable(H4nReflectCheck.List.class)
public @interface H4nReflectCheck {

    String method() ;


    /**
     * 错误消息
     *
     * @return {@code String}
     */
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        H4nReflectCheck[] value();
    }
}
