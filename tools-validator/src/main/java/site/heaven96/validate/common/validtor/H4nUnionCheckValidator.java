package site.heaven96.validate.common.validtor;

import cn.hutool.core.util.ReflectUtil;
import site.heaven96.validate.common.annotation.H4nUnionCheck;
import site.heaven96.validate.service.UnionCheckService;
import site.heaven96.validate.service.impl.UnionCheckServiceImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * H4N联合检查验证器
 *
 * @author Heaven96
 * @date 2021/10/13
 */
public class H4nUnionCheckValidator implements ConstraintValidator<H4nUnionCheck, Object> {
    H4nUnionCheck ca;
    UnionCheckService service;

    @Override
    public void initialize(H4nUnionCheck constraintAnnotation) {
        ca = constraintAnnotation;
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (service == null) {
            service = ReflectUtil.newInstance(UnionCheckServiceImpl.class);
        }
        return service.check(o, ca);
    }
}
