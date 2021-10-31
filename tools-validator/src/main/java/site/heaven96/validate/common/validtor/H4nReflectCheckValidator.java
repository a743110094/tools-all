package site.heaven96.validate.common.validtor;

import site.heaven96.validate.common.annotation.H4nReflectCheck;
import site.heaven96.validate.common.annotation.H4nTbCheck;
import site.heaven96.validate.iface.check.obj.ObjectChecker;
import site.heaven96.validate.imp.check.enums.ObjectCheckModel;
import site.heaven96.validate.imp.check.obj.ObjectCheckerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class H4nReflectCheckValidator  implements ConstraintValidator<H4nReflectCheck, Object> {
    H4nReflectCheck check;

    @Override
    public void initialize(H4nReflectCheck constraintAnnotation) {
        check = constraintAnnotation;
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        ObjectChecker checker = ObjectCheckerFactory.getInstance(ObjectCheckModel.REFLECT);
        return checker.check(check,value);
    }
}
