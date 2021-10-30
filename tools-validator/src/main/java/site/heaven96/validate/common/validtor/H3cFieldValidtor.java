package site.heaven96.validate.common.validtor;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.common.annotation.H4nFieldCheck;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.common.enums.TypeCheckRule;
import site.heaven96.validate.service.FieldCheckService;
import site.heaven96.validate.service.impl.FieldCheckServiceImpl;
import site.heaven96.validate.util.SqlExecutor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * H3C数据校验
 *
 * @author Heaven96
 * @date 2021/09/30
 */
@Slf4j
public class H3cFieldValidtor implements ConstraintValidator<H4nFieldCheck, Object> {

    private static final String DEFAULT_SQL = "SELECT 1 FROM APPS.ORG_ORGANIZATION_DEFINITIONS ood WHERE ood.ORGANIZATION_ID = {} AND ood.DISABLE_DATE IS NULL AND ood.INVENTORY_ENABLED_FLAG = 'Y'";
    private TypeCheckRule rule;
    private String fieldRealName;
    private Logic logic;
    private LegalOrigin legalOrigin;
    private String[] valueSet;
    private String sql;
    private String[] sqlParams;
    private String appendSql;
    private String[] refRetSetFieldName;
    private FieldCheckService fieldCheckService;

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
    public void initialize(H4nFieldCheck constraintAnnotation) {
        rule = constraintAnnotation.rule();
        fieldRealName = constraintAnnotation.columns();
        logic = constraintAnnotation.operator();
        legalOrigin = constraintAnnotation.valueSetOrigin();
        valueSet = constraintAnnotation.valueSet();
        sql = constraintAnnotation.sql();
        sqlParams = constraintAnnotation.sqlParams();
        appendSql = constraintAnnotation.appendSql();
        refRetSetFieldName = constraintAnnotation.refRetSetFields();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     * <p>
     * This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return doValid(value);
    }

    /**
     * 验证参数是否有效
     *
     * @param obj 参数
     * @return boolean
     */
    private boolean doValid(Object obj) {
        switch (rule) {
            case 检查库存组织代码是否正确_存在性验证: {
                if (ObjectUtils.isEmpty(obj)) {
                    return false;
                }
                return NumberUtil.compare(SqlExecutor.count(DEFAULT_SQL, obj), 0) == 1;
            }
            default: {
                if (fieldCheckService == null) {
                    fieldCheckService = ReflectUtil.newInstance(FieldCheckServiceImpl.class);
                }
                return fieldCheckService.check(obj, rule, fieldRealName, logic, legalOrigin
                        , valueSet, sql, sqlParams, appendSql, refRetSetFieldName);
            }
        }
    }


}
