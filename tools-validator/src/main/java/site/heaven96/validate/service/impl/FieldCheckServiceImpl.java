package site.heaven96.validate.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.assertes.common.exception.H4nBeforeValidateCheckException;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.common.enums.TypeCheckRule;
import site.heaven96.validate.service.FieldCheckService;
import site.heaven96.validate.util.H4nCompareUtil;
import site.heaven96.validate.util.SqlExecutor;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段检查服务类
 *
 * @apiNote 针对单个字段进行检验
 *          比如 :
 *          class User{
 *              @H4nFieldCheck
 *              private String name;
 *          }
 *          校验规则可以是 name 在给定的范围内，或者是某个静态sql的执行结果
 *          （此方法是为了补充hibernate-validator的功能）
 * @author Heaven96
 * @date 2021/10/11
 *
 */
//TODO 测试
@Slf4j(topic = "[字段校验服务 FieldCheckServiceImpl]")
public class FieldCheckServiceImpl implements FieldCheckService {

    public static final String FEILD_ENTRACE_LOG_MSG =
            "\n===> 开始进行字段级别检查 涉及字段[{}] 字段值[{}] " +
                    "\n =>逻辑运算符[{}]" +
                    "\n =>值集来源[{}]" +
                    "\n =>静态值集[{}] " +
                    "\n =>动态值集[{}] " +
                    "\n =>sql[{}] ";
    public static final String FEILD_UNDEFIND_VALUE_SET_ORIGIN_ERR_MSG = "==>进行字段校验时，未指定值集来源（LegalOrigin）";
    public static final String FIELD_UNSUPPORT_NOW_ERR_MSG = "暂不支持";
    public static final String 进行验证的值为空 = "进行验证的值为空";

    /**
     * Fun10 固定值校验
     *
     * @param obj      OBJ
     * @param valueSet 值集
     * @param logic    操作员
     * @return boolean
     * @see {@filedCheck}
     */
    @Deprecated
    public static boolean fun10(Object obj, Logic logic, String[] valueSet) {
        if (valueSet.length == 0) {
            return false;
        }
        switch (logic) {
            case EQUALS: {
            }
            case IN: {
//针对字符 字符串
                return Arrays.stream(valueSet).anyMatch(str -> ObjectUtil.equals(obj, str) || NumberUtil.equals(new BigDecimal(StrUtil.str(obj, StandardCharsets.UTF_8)), new BigDecimal(str)));
            }
            case NOT_EQUALS: {
            }
            case NOT_IN: {
                return Arrays.stream(valueSet).noneMatch(str -> ObjectUtil.equals(obj, str) || NumberUtil.equals(new BigDecimal(StrUtil.str(obj, StandardCharsets.UTF_8)), new BigDecimal(str)));
            }
            case LESS_THAN: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return NumberUtil.isLess(new BigDecimal(StrUtil.toString(obj)), new BigDecimal(valueSet[0]));
            }
            case LESS_THAN_OR_EQUAL_TO: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return NumberUtil.isLessOrEqual(new BigDecimal(StrUtil.toString(obj)), new BigDecimal(valueSet[0]));
            }
            case GREATER_THAN: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return NumberUtil.isGreater(new BigDecimal(StrUtil.toString(obj)), new BigDecimal(valueSet[0]));
            }
            case GREATER_THAN_OR_EQUALS: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return NumberUtil.isGreaterOrEqual(new BigDecimal(StrUtil.toString(obj)), new BigDecimal(valueSet[0]));
            }
            case BETWEEN_AND: {
                String expression = valueSet[0].trim();//like [1,2)
                char leftExp = expression.charAt(0);
                char rightExp = expression.charAt(expression.length() - 1);
                int commaIndex = expression.indexOf(',');
                // 40 (  41 ) 91 [ 93 ]
                if ((leftExp == 40 || leftExp == 91) && (rightExp == 41 || rightExp == 93)) {
                    String minVal = expression.substring(1, commaIndex);
                    String maxVal = expression.substring(commaIndex + 1, expression.length() - 1);
                    int compareMin = ObjectUtil.compare(StrUtil.toString(obj), minVal);
                    int compareMax = ObjectUtil.compare(StrUtil.toString(obj), maxVal);
                    if (compareMin < 0 || compareMax > 0) {
                        //比最小值小 比最大值大
                        return false;
                    }
                    //开区间 踩在区间上
                    return (compareMin != 0 || leftExp != 40) && (compareMax != 0 || rightExp != 41);
                } else {
                    throw new H4nBeforeValidateCheckException("BETWEEN校验时，区间表达式有误");
                }
            }
            default: {
                throw new H4nBeforeValidateCheckException("未知逻辑运算符");
            }
        }
    }


    /**
     * 单Field检查
     *
     * @param obj      OBJ
     * @param logic    操作员
     * @param valueSet 值集
     * @return boolean
     * @date 2021年10月23日 02:31:24
     */
    public static boolean filedCheck(Object obj, Logic logic, boolean ignoreCase, String[] valueSet) {
        if (logic.isRequireValueSet() && ArrayUtil.isEmpty(valueSet)) {
            return false;
        }
        switch (logic) {
            case HAS_TEXT: {
                return H4nCompareUtil.hasText(obj);
            }
            case HAS_NO_TEXT: {
                return H4nCompareUtil.hasNoText(obj);
            }
            case IS_NULL: {
                return ObjectUtil.isNull(obj);
            }
            case NOT_NULL: {
                return ObjectUtil.isNotNull(obj);
            }
            case EQUALS: {
                return H4nCompareUtil.equalsB(obj, valueSet[0], ignoreCase);
            }
            case IN: {
                //针对 字符串 数字 等对象  比如部门必须是XXX  出生年必须是xxxx和XXXX
                return H4nCompareUtil.In(obj, valueSet, ignoreCase);
            }
            case NOT_EQUALS: {
            }
            case NOT_IN: {
                return H4nCompareUtil.notIn(obj, valueSet, ignoreCase);
            }
            case LESS_THAN: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return H4nCompareUtil.isLess(obj,valueSet[0]);
            }
            case LESS_THAN_OR_EQUAL_TO: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return H4nCompareUtil.lessOrEquals(obj,valueSet[0]);
            }
            case GREATER_THAN: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return H4nCompareUtil.isGreater(obj,valueSet[0]);
            }
            case GREATER_THAN_OR_EQUALS: {
                //比较结果，如果c1 < c2，返回数小于0，c1==c2返回0，c1 > c2 大于0
                return H4nCompareUtil.greaterOrEquals(obj,valueSet[0]);
            }
            case BETWEEN_AND: {
                //TODO 整合进 H4nCompareUtil
                String expression = valueSet[0].trim();//like [1,2)
                char leftExp = expression.charAt(0);
                char rightExp = expression.charAt(expression.length() - 1);
                int commaIndex = expression.indexOf(',');
                // 40 (  41 ) 91 [ 93 ]
                if ((leftExp == 40 || leftExp == 91) && (rightExp == 41 || rightExp == 93)) {
                    String minVal = expression.substring(1, commaIndex);
                    String maxVal = expression.substring(commaIndex + 1, expression.length() - 1);
                    boolean objIsLessThanMinVal = H4nCompareUtil.isLess(obj, minVal);
                    boolean objIsGreaterThanMaxVal = H4nCompareUtil.isGreater(obj, maxVal);
                    if (objIsLessThanMinVal || objIsGreaterThanMaxVal) {
                        //比最小值小 比最大值大
                        return false;
                    }
                    //开区间 踩在区间上
                    return (H4nCompareUtil.notEquals(obj, minVal) || leftExp != 40) && (H4nCompareUtil.notEquals(obj, maxVal) || rightExp != 41);
                } else {
                    throw new H4nBeforeValidateCheckException("BETWEEN校验时，区间表达式有误");
                }
            }
            default: {
                throw new H4nBeforeValidateCheckException("未知逻辑运算符");
            }
        }
    }


    /**
     * 检查
     * 检查入口
     *
     * @param rule               规则
     * @param fieldRealName      字段实名
     * @param logic              逻辑运算符
     * @param legalOrigin        值集来源
     * @param valueSet           静态值集
     * @param sql                SQL
     * @param sqlParams          SQL参数
     * @param appendSql          追加SQL
     * @param refRetSetFieldName 传入值集参考字段
     * @param obj                OBJ
     * @return boolean
     */
    @Override
    public boolean check(Object obj, TypeCheckRule rule, String fieldRealName, Logic logic, LegalOrigin legalOrigin, String[] valueSet, String sql, String[] sqlParams, String appendSql, String[] refRetSetFieldName) {
        /** 打印日志 */
        logger(obj, rule, fieldRealName, logic, legalOrigin, valueSet, sql, sqlParams, appendSql, refRetSetFieldName);
        /** 验证结果 */
        boolean flag = false;
        /** 计算用于最后验证的值集合 */
        List<Object> valueList = new ArrayList<>();
        switch (legalOrigin) {
            case FIXED: {
                //Assert.notNull(obj, 进行验证的值为空);
                //遍历固定值集寻找字段值
                flag = fun10(obj, logic, valueSet);
                break;
            }
            case DYNAMIC:
            case SQL: {
                //TODO 推后 因为无法取到参数
                //TODO 收不到动态参数 暂时
                flag = fun20(obj, logic, sql);
                break;
                //throw new H4nBeforeValidateCheckException(FIELD_UNSUPPORT_NOW_ERR_MSG);
            }
            default: {
                log.error(FEILD_UNDEFIND_VALUE_SET_ORIGIN_ERR_MSG);
                throw new H4nBeforeValidateCheckException(FEILD_UNDEFIND_VALUE_SET_ORIGIN_ERR_MSG);
            }
        }
        log.info("\n===> 字段级别检查,结果{}", flag);
        return flag;
    }

    //TODO//TODO//TODO//TODO
    public boolean check2(Object obj, TypeCheckRule rule, String fieldRealName, Logic logic, LegalOrigin legalOrigin, String[] valueSet, String sql, String[] sqlParams, String appendSql, String[] refRetSetFieldName) {
        /** 打印日志 */
        logger(obj, rule, fieldRealName, logic, legalOrigin, valueSet, sql, sqlParams, appendSql, refRetSetFieldName);
        /** 验证结果 */
        boolean flag = false;
        /** 计算用于最后验证的值集合 */
        List<String> valueList = new ArrayList<>();

        switch (legalOrigin) {
            case FIXED: {
                //Assert.notNull(obj, 进行验证的值为空);
                //遍历固定值集寻找字段值
                flag = fun10(obj, logic, valueSet);
                break;
            }
            case DYNAMIC:
            case SQL: {
                //TODO 推后 因为无法取到参数
                //TODO 收不到动态参数 暂时
                flag = fun20(obj, logic, sql);
                break;
                //throw new H4nBeforeValidateCheckException(FIELD_UNSUPPORT_NOW_ERR_MSG);
            }
            default: {
                log.error(FEILD_UNDEFIND_VALUE_SET_ORIGIN_ERR_MSG);
                throw new H4nBeforeValidateCheckException(FEILD_UNDEFIND_VALUE_SET_ORIGIN_ERR_MSG);
            }
        }
        log.info("\n===> 字段级别检查,结果{}", flag);
        return flag;
    }

    /**
     * 值列表 计算用于同字段值进行比对的标准集合 //TODO//TODO//TODO//TODO
     *
     * @return {@code List<String>}
     */
    private List<String> valueList(String fieldRealName, Logic logic, LegalOrigin legalOrigin,
                                   String[] valueSet, String sql, String[] sqlParams, String appendSql,
                                   String[] refRetSetFieldName) {
        List<String> valueList = new ArrayList<>();

        if (Arrays.stream(refRetSetFieldName).count() == 0) {
            //如果依赖字段为空 说明不依赖任其他字段的值 则基于单字段判定即可
            if (legalOrigin.equals(LegalOrigin.FIXED)) {
                valueList = Arrays.stream(valueSet).collect(Collectors.toList());
            }
        } else {
            //如果依赖字段不为空 要基于其他字段来判断
            //---------------------获取注解所在类
            Class<?> className = null;
            //TODO ...........

            //---------------------获取注解所在类
            /** 依赖字段数目 此处>=1 */
            int refNum = refRetSetFieldName.length;
            // for 依次计算各字段值
            for (int i = 0; i < refNum; i++) {
                /** 第 i 个字段 路径 */
                String itemFieldPath = refRetSetFieldName[i].trim();
                /** 一级一级按照小数点拆开 */
                String[] itemFieldArray = itemFieldPath.split("\\.");
                /**  第一级是否为collection */
                boolean zeroLevelIsCollection = false;

            }
        }

        return null;
    }

    /**
     * Fun20
     *
     * @param obj   OBJ
     * @param logic 操作员
     * @param sql   SQL
     * @return boolean
     */
    //todo  fieldRealName sqlParams appendSql refRetSetFieldName
    public boolean fun20(Object obj, Logic logic, String sql) {
        List<String> strings = SqlExecutor.selectStrs(sql);
        String[] objects = strings.toArray(new String[strings.size()]);
        return fun10(obj, logic, objects);
    }

    /**
     * 记录器
     */
    private void logger(Object obj, TypeCheckRule rule, String fieldRealName, Logic logic,
                        LegalOrigin legalOrigin, String[] valueSet, String sql, String[] sqlParams,
                        String appendSql, String[] refRetSetFieldName) {
      /*  涉及字段[{}] 字段值[{}] " +
        "\n =>逻辑运算符[{}]" +
                "\n =>值集来源[{}]" +
                "\n =>静态值集[{}] " +
                "\n =>动态值集[{}] " +
                "\n =>sql[{}] */
        log.info(FEILD_ENTRACE_LOG_MSG, null, obj, legalOrigin, valueSet, null, sql);
    }


}
