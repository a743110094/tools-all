package site.heaven96.validate.common.validtor;


import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.common.annotation.H4nTbCheck;
import site.heaven96.validate.common.enums.TbCheck;
import site.heaven96.validate.common.factory.H3cValidtorFactory;
import site.heaven96.validate.imp.check.enums.ObjectCheckModel;
import site.heaven96.validate.imp.check.obj.ObjectCheckerFactory;
import site.heaven96.validate.service.H3cWmsValidateService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * H3C表数据约束校验
 *
 * @author Heaven96
 * @date 2021/09/30
 */
@Slf4j
public class H4nTbCheckValidator implements ConstraintValidator<H4nTbCheck, Object> {

    /**
     * H3C WMS验证服务
     */
    private H4nTbCheck h4nTbCheck;

    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     * <p>
     * This method is guaranteed to be called before any use of this instance for
     * validation.
     * <p>
     * The default implementation is a no-op.
     *
     * @param check annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(H4nTbCheck check) {
        h4nTbCheck = check ;
        ConstraintValidator.super.initialize(check);
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     * 实现验证逻辑。 value状态不得改变。
     * 该方法可以并发访问，必须通过实现来保证线程安全。
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return doValid(value);
    }


    /**
     * 验证唯一性
     *
     * @return boolean
     */
    private boolean doValid(Object obj) {
        return ObjectCheckerFactory.getInstance(ObjectCheckModel.TB_CHECK).check(h4nTbCheck,obj);
    }


}
