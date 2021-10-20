package site.heaven96.validate.common.validtor;


import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.common.annotation.H4nTbCheck;
import site.heaven96.validate.common.enums.TbCheck;
import site.heaven96.validate.common.factory.H3cValidtorFactory;
import site.heaven96.validate.service.H3cWmsValidateService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * H3C表数据约束校验
 *
 * @author lgw3488
 * @date 2021/09/30
 */
@Slf4j
public class H3cTbCheckValidtor implements ConstraintValidator<H4nTbCheck, Object> {
    /**
     * 约束类型
     */
    private TbCheck check;
    /**
     * 模式
     */
    private String schema;

    /**
     * 表名
     */
    private String table;

    /**
     * Java属性名称
     */
    private String[] fields;

    /**
     * 表字段名称
     */
    private String[] columns;

    /**
     * 追加SQL
     */
    private String appendSql;

    /**
     * H3C WMS验证服务
     */
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
     * @param ca annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(H4nTbCheck ca) {
        schema = ca.schema();
        table = ca.tableName();
        fields = ca.propertyNames();
        columns = ca.realFieldName();
        check = ca.check();
        appendSql = ca.appendSql();
        if (h3cWmsValidateService == null) {
            h3cWmsValidateService = H3cValidtorFactory.getInstance("H3cWmsValidateService");
        }
        ConstraintValidator.super.initialize(ca);
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
     * 验证唯一性
     *
     * @return boolean
     */
    private boolean doValid(Object obj) {
        return h3cWmsValidateService.tbValidator(check, obj, schema, table, fields, columns, appendSql);
    }


}
