package site.heaven96.validate.imp.check.obj;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.annotation.H4nReflectCheck;
import site.heaven96.validate.common.annotation.H4nUnionCheck;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.iface.check.field.FieldChecker;
import site.heaven96.validate.imp.check.enums.FieldCheckModel;
import site.heaven96.validate.imp.check.field.FiledCheckerFactory;
import site.heaven96.validate.util.FieldUtil;
import site.heaven96.validate.util.SpelUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 反射检查器
 * @author lgw3488
 * @date 2021/10/29
 */
@Slf4j
public class ReflectCheckerImpl extends AbObjectChecker<H4nReflectCheck> {
    /**
     * 检查
     *
     * @param ckAnnotation 注解
     * @param obj          被检查对象
     * @return boolean
     */
    @Override
    public boolean check(H4nReflectCheck ckAnnotation, Object obj) {
            String method = ckAnnotation.method();
            return ClassUtil.invoke(method, new Object[]{obj});
    }
}
