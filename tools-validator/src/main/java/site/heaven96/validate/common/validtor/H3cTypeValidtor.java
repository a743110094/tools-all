package site.heaven96.validate.common.validtor;


import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.common.annotation.H4nTypeCheck;
import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.common.enums.Relation;
import site.heaven96.validate.common.enums.TypeCheckRule;
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
public class H3cTypeValidtor implements ConstraintValidator<H4nTypeCheck, Object> {

    private TypeCheckRule rule;

    private String sql;

    private String[] fieldsA;

    private String[] fieldsB;

    private Relation relation;

    private Operator operator;

    private String[] resultSet;

    private String[] fieldNames;

    private H3cWmsValidateService h3cWmsValidateService;


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
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(H4nTypeCheck constraintAnnotation) {
        rule = constraintAnnotation.rule();
        fieldNames = constraintAnnotation.fieldNames();
        fieldsA = constraintAnnotation.fieldNamesA();
        fieldsB = constraintAnnotation.fieldNamesB();
        relation = constraintAnnotation.relationshipA2B();
        operator = constraintAnnotation.operator();
        resultSet = constraintAnnotation.resultSet();
        sql = constraintAnnotation.sql();
        if (h3cWmsValidateService == null) {
            h3cWmsValidateService = H3cValidtorFactory.getInstance("H3cWmsValidateServiceImpl");
        }
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * 实现验证逻辑。 value状态不得改变。
     * 该方法可以并发访问，必须通过实现来保证线程安全。
     *
     * @param obj     要验证的对象
     * @param context 在其中评估约束的上下文
     * @return {@code false} if {@code val ue} does not pass the constraint
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        return doValid(obj);
    }

    /**
     * 验证参数是否有效
     *
     * @param obj 参数
     * @return boolean
     */
    private boolean doValid(Object obj) {
        if (h3cWmsValidateService == null) {
            h3cWmsValidateService = H3cValidtorFactory.getInstance("H3cWmsValidateServiceImpl");
        }
        if (rule.equals(TypeCheckRule.自定义)) {
            return h3cWmsValidateService.typeValidator(sql, fieldsA, fieldsB, relation, operator, resultSet, obj);
        } else {
            return h3cWmsValidateService.typeValidator(rule, fieldNames, obj);
        }
    }


}
