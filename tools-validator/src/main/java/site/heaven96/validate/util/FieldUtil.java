package site.heaven96.validate.util;

import cn.hutool.core.bean.BeanUtil;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.exception.H4nUnExpectedException;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段属性实用程序
 *
 * @author lgw3488
 * @date 2021/10/18
 */
public class FieldUtil {

    public static final String FU_OBJECT_IS_NOT_BEAN_ERR_MSG = "\n===>获取Bean中包含H4nCheck注解的字段失败：给定的对象不是Bean";

    /**
     * 获取带注释的组字段列表
     * 获取一个 bean 中 指定组号 带 H4nCheck 注释的 字段列表
     *
     * @param bean  豆子
     * @param group 组
     * @return {@code List<Field>}
     */
    public static List<Field> getGroupFieldListWithAnnotation(@NotNull final Object bean, @NotNull final int group) {
        AssertUtil.isTrueThrowH4nBeforeValidateCheckException(BeanUtil.isBean(bean.getClass()), FU_OBJECT_IS_NOT_BEAN_ERR_MSG);
        try {
            return Arrays.stream(bean.getClass().getDeclaredFields())
                    .filter(field -> Arrays.stream(field.getDeclaredAnnotationsByType(H4nCheck.class))
                            .distinct()
                            .anyMatch(item -> item.group() == group))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new H4nUnExpectedException(e.getMessage());
        }
    }
}
