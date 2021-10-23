package site.heaven96.validate.service;


import site.heaven96.validate.common.enums.*;

/**
 * h3c WMS验证服务 接口
 *
 * @author Heaven96
 * @date 2021/10/06
 */
public interface H3cWmsValidateService {
    /**
     * 字段验证器
     *
     * @return boolean
     */
    boolean fieldValidtor();

    /**
     * 类型验证器
     *
     * @param rule   规则
     * @param fields 字段
     * @param args   参数
     * @return boolean
     */
    boolean typeValidator(TypeCheckRule rule, String[] fields, Object args);

    /**
     * 类型验证器
     * 类型验证器 新
     *
     * @param fieldsA   fieldsA
     * @param fieldsB   fieldsB
     * @param relation  关系
     * @param obj       参数
     * @param sql       SQL
     * @param operator  操作员
     * @param resultSet 结果集
     * @return boolean
     */
    boolean typeValidator(String sql, String[] fieldsA, String[] fieldsB, Relation relation, Operator operator, String[] resultSet, Object obj);


    /**
     * TB验证器
     * TB唯一验证器
     *
     * @param schema        模式
     * @param tableName     表名
     * @param propertyNames 属性名称
     * @param realFieldName 字段名称
     * @param obj           OBJ
     * @param check         检查
     * @param appendSql     追加SQL
     * @return boolean
     */
    boolean tbValidator(TbCheck check, Object obj, String schema, String tableName, String[] propertyNames, String[] realFieldName, String appendSql);

    /**
     * 字段需要验证器
     *
     * @param target      目标
     * @param reference   参考文献
     * @param requireRule 需要规则
     * @param valueSet    值集
     * @param obj         OBJ
     * @param note        注意事项
     * @return boolean
     */
    boolean fieldRequireValidator(Object obj, String target, String[] reference, RequireRule requireRule, String[] valueSet, String note);
}
