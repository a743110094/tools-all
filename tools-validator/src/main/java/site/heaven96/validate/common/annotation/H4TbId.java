package site.heaven96.validate.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * H4表主键<br>
 * 配合{@link site.heaven96.validate.common.annotation.H4nTbCheck}注解使用<br>用于非mybatis plus 或者 beetlsql 情况下 指定bean的主键
 * @author Heaven96
 * @date 2021/10/14
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface H4TbId {
}
