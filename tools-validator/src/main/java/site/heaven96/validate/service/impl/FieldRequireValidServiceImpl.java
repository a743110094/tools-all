package site.heaven96.validate.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.common.constant.ValidtorConstants;
import site.heaven96.validate.common.enums.*;
import site.heaven96.validate.common.exception.H4nBeforeValidateCheckException;
import site.heaven96.validate.service.H3cWmsValidateService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 字段必填校验服务实现
 *
 * @author Heaven96
 * @date 2021/10/09
 */
@Slf4j
public class FieldRequireValidServiceImpl implements H3cWmsValidateService {

    /**
     * Fun40 任一参考字段存在于值集则目标字段必填
     *
     * @param obj       OBJ
     * @param target    目标
     * @param reference 参考文献
     * @param valueSet  值集
     * @return boolean
     */
    private static boolean fun40(Object obj, String target, String[] reference, String[] valueSet) {
        //合法值集
        List<String> legalValue = Arrays.asList(valueSet).stream().map(StrUtil::trim).collect(Collectors.toList());
        Iterator<String> it = Arrays.stream(reference).iterator();
        //遍历参考字段
        while (it.hasNext()) {
            //参考字段的值
            String rfv = StrUtil.str(BeanUtil.getFieldValue(obj, it.next()), "utf8");
            if (legalValue.contains(rfv)) {
                //目标字段的值 是否为空
                return StrUtil.isNotBlank(StrUtil.str(BeanUtil.getFieldValue(obj, target), "utf8"));
            }
        }
        return true;
    }

    /**
     * 字段验证器
     *
     * @return boolean
     */
    @Override
    public boolean fieldValidtor() {
        return false;
    }

    /**
     * 类型验证器
     *
     * @param rule   规则
     * @param fields 字段
     * @param args   参数
     * @return boolean
     */
    @Override
    public boolean typeValidator(TypeCheckRule rule, String[] fields, Object args) {
        return false;
    }

    /**
     * 类型验证器
     * 类型验证器 新
     *
     * @param sql       SQL
     * @param fieldsA   fieldsA
     * @param fieldsB   fieldsB
     * @param relation  关系
     * @param operator  操作员
     * @param resultSet 结果集
     * @param obj       参数
     * @return boolean
     */
    @Override
    public boolean typeValidator(String sql, String[] fieldsA, String[] fieldsB, Relation relation, Operator operator, String[] resultSet, Object obj) {
        return false;
    }

    /**
     * TB唯一验证器
     *
     * @param check         检查
     * @param obj           OBJ
     * @param schema        模式
     * @param tableName     表名
     * @param propertyNames 属性名称
     * @param realFieldName 字段名称
     * @return boolean
     */
    @Override
    public boolean tbValidator(TbCheck check, Object obj, String schema, String tableName, String[] propertyNames, String[] realFieldName, String appendSql) {
        return false;
    }

    /**
     * 字段需要验证器
     *
     * @param obj         OBJ
     * @param target      目标
     * @param reference   参考文献
     * @param requireRule 需要规则
     * @param valueSet    值集
     * @return boolean
     */
    @Override
    public boolean fieldRequireValidator(Object obj, String target, String[] reference, RequireRule requireRule, String[] valueSet, String note) {
        Assert.isTrue(BeanUtil.hasGetter(obj.getClass()), ValidtorConstants.REQ_NO_GETTER_ERR_MSG);
        Assert.notNull(requireRule, ValidtorConstants.REQ_NO_RULE_FOUND_ERR_MSG);
        log.info(ValidtorConstants.REQ_DEFAULT_ENTRANCE_LOG, requireRule.toString(), note);
        switch (requireRule) {
            case TARGET_MUST_IN_VALUE_SET: {
                return fun10(obj, target, valueSet);
            }
            case WHEN_REF_ALL_NULL_THEN_REQUIRE_TARGET: {
                return fun20(obj, target, reference);
            }
            case WHEN_REF_HAS_NULL_THEN_REQUIRE_TARGET: {
                return fun30(obj, target, reference);
            }
            case ANY_REF_IN_VALUE_SET_THEN_REQUIRE_TARGET: {
                return fun40(obj, target, reference, valueSet);
            }
            default: {
                throw new H4nBeforeValidateCheckException(ValidtorConstants.REQ_NO_RULE_FOUND_ERR_MSG);
            }
        }
    }

    /**
     * Fun10 目标字段值域限制 目标字段可以是单个值或者Collection的实现
     *
     * @param obj      OBJ
     * @param target   目标
     * @param valueSet 值集
     * @return boolean
     */
    private boolean fun10(Object obj, String target, String[] valueSet) {
        //获取目标字段值
        Object o = BeanUtil.getFieldValue(obj, target);
        //合法值集
        List<String> legalValue = Arrays.asList(valueSet).stream().map(StrUtil::trim).collect(Collectors.toList());
        if (o instanceof Collection) {
            Iterator<?> it = ((Collection<?>) o).iterator();
            while (it.hasNext()) {
                Object o1 = it.next();
                boolean legal = legalValue.contains(StrUtil.str(o1, "utf8").trim());
                if (!legal) {
                    return false;
                }
            }
            return true;
        } else {
            return legalValue.contains(StrUtil.str(o, "utf8").trim());
        }
    }

    /**
     * Fun20 参考字段全空则目标字段必填
     *
     * @return boolean
     */
    private boolean fun20(Object obj, String target, String[] reference) {
        Iterator<String> it = Arrays.stream(reference).iterator();
        //遍历参考字段
        while (it.hasNext()) {
            //参考字段 发现非空 则通过校验
            if (StrUtil.isNotBlank(it.next())) {
                return true;
            }
        }
        //目标字段的值 是否为空
        return StrUtil.isNotBlank(StrUtil.str(BeanUtil.getFieldValue(obj, target), "utf8"));
    }

    /**
     * Fun30 参考字段存在空值则目标字段必填
     *
     * @return boolean
     */
    private boolean fun30(Object obj, String target, String[] reference) {
        Iterator<String> it = Arrays.stream(reference).iterator();
        //遍历参考字段
        while (it.hasNext()) {
            //参考字段 发现非空
            if (StrUtil.isNotBlank(it.next())) {
                return StrUtil.isNotBlank(StrUtil.str(BeanUtil.getFieldValue(obj, target), "utf8"));
            }
        }
        //所有字段都非空
        return true;
    }
}