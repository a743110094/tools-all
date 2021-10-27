package site.heaven96.validate.common.annotation;


import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.common.enums.TypeCheckRule;
import site.heaven96.validate.common.enums.ValueSetOrigin;
import site.heaven96.validate.common.validtor.H3cFieldValidtor;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * H4N 单个字段校验（不涉及其他字段）
 * <p>
 * 思路：某个字段收到约束 分为定值约束，sql结果集约束和动态传值约束三种
 * 定值约束{@ValueSetOrigin.FIXED_VALUE} 此字段的值必须在resultSet中
 * 定值约束{@ValueSetOrigin.SQL_RESULTS} 此字段的值必须在SQL（静态）执行的结果中
 *
 * @author Heaven96
 * @date 2021/09/30
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = H3cFieldValidtor.class)
public @interface H4nFieldCheck{
    /**
     * 校验类型
     *
     * @return {@code String}
     */
    @Deprecated
    TypeCheckRule rule() default TypeCheckRule.VALUE_SET;

    /**
     * 属性注释 字段的含义 用于打印日志
     *
     * @return {@code String}
     */
    String fieldNote() default "";

    /**
     * 字段名
     * 属性和字段名非驼峰转换规则时使用
     *
     * @return {@code String}
     */
    String columns() default "";

    /**
     * 运算符
     * 表述字段值和valueSet之间的逻辑关系
     *
     * @return {@code Logic}
     */
    Logic operator() default Logic.EQUALS;

    /**
     * 值集来源
     *
     * @ValueSetOrigin.FIXED_VALUE //TODO 基于此分路判断
     */
    ValueSetOrigin valueSetOrigin() default ValueSetOrigin.FIXED_VALUE;

    /**
     * 值集
     *
     * @return {@code String[]}
     */
    String[] valueSet() default {};

    /**
     * SQL 备用
     *
     * @return {@code String}
     */
    String sql() default "SELECT 1 FROM DUAL";

    /**
     * SQL参数
     * sql需要传入参数时，指定该类下面的属性名，则会取属性值按顺序填充到sql的参数中
     * **********************************************
     * 例如：要限定major的值为 ”软件工程“ 才能通过校验
     * class User{
     * Integer id;
     * String name;
     * String grade;
     * String major;
     * }
     *
     * @return {@code String[]}
     * @sql = select major from tb_user where id = ? ;
     * @sqlParams = {"id"};
     * @valueSet = {"软件工程"}
     * User a = new User(1,"张三");
     * 对应执行SQL为：
     * select * from tb_user where id = 1 ;
     * **********************************************
     */
    String[] sqlParams() default {};

    /**
     * 追加SQL
     * 当sql需要追加固定sql时使用
     * **********************************************
     * 例如：上述例子中，如果还要要求必须是大一年级
     *
     * @return {@code String}
     * @appendSql = {"grade = '大一'"}
     * 对应执行SQL为：
     * select major from tb_user where id = 1 AND grade = '大一';
     * **********************************************
     */
    String appendSql() default "";

    /**
     * 动态值集参考字段
     * {@ValueSetOrigin.DYNAMIC_SPECIFIED_VALUE}时，指定属性名，得到对应的合法属性值
     *
     * @return {@code String}
     */
    String[] refRetSetFields() default "";

    /**
     * 错误消息
     *
     * @return {@code String}
     */
    String message() default "默认消息：{propertyNote}字段验证失败，[${validatedValue}]不是合法值 (验证模式[{valueSetOrigin}] 逻辑[{operator}]，详见日志信息)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
