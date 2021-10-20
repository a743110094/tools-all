package site.heaven96.validate.service;

import site.heaven96.validate.common.enums.Operator;
import site.heaven96.validate.common.enums.TypeCheckRule;
import site.heaven96.validate.common.enums.ValueSetOrigin;

/**
 * 字段检查服务 用作单个字段的检查
 *
 * @author lgw3488
 * @date 2021/10/11
 */
public interface FieldCheckService {

    /**
     * 检查
     *
     * @param rule               规则
     * @param fieldRealName      字段实名
     * @param operator           逻辑运算符
     * @param valueSetOrigin     值集来源
     * @param valueSet           静态值集
     * @param sql                SQL
     * @param sqlParams          SQL参数
     * @param appendSql          追加SQL
     * @param refRetSetFieldName 传入值集参考字段
     * @param obj                OBJ
     * @return boolean
     */
    boolean check(Object obj,
                  TypeCheckRule rule,
                  String fieldRealName,
                  Operator operator,
                  ValueSetOrigin valueSetOrigin,
                  String[] valueSet,
                  String sql,
                  String[] sqlParams,
                  String appendSql,
                  String[] refRetSetFieldName
    );
}
