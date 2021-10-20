package site.heaven96.validate.common.validtor;


import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.common.annotation.H4nUnionFieldVerify;
import site.heaven96.validate.common.enums.RequireRule;
import site.heaven96.validate.common.factory.H3cValidtorFactory;
import site.heaven96.validate.service.H3cWmsValidateService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * H3C数据校验
 *
 * @author lgw3488
 * @date 2021/09/30
 */
@Slf4j
public class FieldRequireValidtor implements ConstraintValidator<H4nUnionFieldVerify, Object> {
    /**
     * 目标领域
     */
    private String targetFeilds;

    /**
     * 参考字段
     */
    private String[] referenceFields;

    /**
     * 规则
     */
    private RequireRule rule;

    /**
     * 值集
     */
    private String[] valueSet;

    /**
     * 备注
     */
    private String note;

    /**
     * H3C WMS验证服务
     */
    private H3cWmsValidateService service;

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
     * @param ca annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(H4nUnionFieldVerify ca) {
        referenceFields = ca.referenceFields();
        targetFeilds = ca.targetFeilds();
        valueSet = ca.valueSet();
        rule = ca.rule();
        note = ca.note();
        ConstraintValidator.super.initialize(ca);
    }

    /**
     * 是有效的
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param context context in which the constraint is evaluated
     * @param obj     OBJ
     * @return boolean
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return doValid(obj);
    }

    /**
     * 是否有效？
     * 验证参数是否有效
     *
     * @param obj OBJ
     * @return boolean
     */
    private boolean doValid(Object obj) {
        if (service == null) {
            service = H3cValidtorFactory.getInstance("FieldRequireValidServiceImpl");
        }
        return service.fieldRequireValidator(obj, targetFeilds, referenceFields, rule, valueSet, note);

    }


}
