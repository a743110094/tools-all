package site.heaven96.validate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import site.heaven96.validate.common.constant.ValidtorConstants;
import site.heaven96.validate.common.enums.*;
import site.heaven96.validate.service.H3cWmsValidateService;
import site.heaven96.validate.util.SqlExecutor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static site.heaven96.validate.common.constant.ValidtorConstants.TB_BEGIN_VALIDATE_NOTICE_INFO;
import static site.heaven96.validate.common.constant.ValidtorConstants.TB_PARAMS_CHECK_ERR_MSG;


/**
 * H3C WMS验证服务实现
 *
 * @author Heaven96
 * @date 2021/10/08
 */
@Slf4j(topic = "【H3C数据验证服务】")
public class H3cWmsValidateServiceImpl implements H3cWmsValidateService {


    /**
     * 类型原木入口
     */
    public static final String TYPE_LOG_ENTRACE = "\n===> 开始进行[自定义]校验，涉及字段A[{}]，字段B[{}]\n =>逻辑[{}]，值集[{}]";

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
     * 类型验证器1 旧版
     *
     * @param rule   规则
     * @param fields 字段
     * @param obj    参数
     * @return boolean
     */
    @Override
    public boolean typeValidator(TypeCheckRule rule, String[] fields, Object obj) {
        log.info(ValidtorConstants.TYPE_BEGIN_VALIDATE_NOTICE_INFO, rule.toString());
        switch (rule) {
            case 检查工单ID是否正确: {
                return fun1(obj, fields);
            }
            case 检查子库是否为VMI子库: {
                return fun2(obj, fields);
            }
            default: {
                return true;
            }
        }
    }

    /**
     * 类型验证器
     * 类型验证器2 通用  自定义  新版
     *
     * @param fieldsA  fieldsA
     * @param fieldsB  fieldsB
     * @param relation 关系
     * @param obj      参数
     * @param sql      SQL
     * @return boolean
     */
    @Override
    public boolean typeValidator(String sql, String[] fieldsA, String[] fieldsB, Relation relation, Operator operator, String[] resultSet, Object obj) {
        log.info(TYPE_LOG_ENTRACE, fieldsA, fieldsB, operator, resultSet);
        boolean checkPass = typeValidtorparaChecker(sql, fieldsA, fieldsB);
        if (!checkPass) {
            log.error("SQL要求的参数个数(?数目)和实际提供的参数个数不匹配");
            return false;
        }
        /** TODO 判断A B的首层是否为Collection接口的实现 */
        //List<Object> argsA = getArgsSingle(obj,fieldsA);
        List<List<Object>> argsA = getArgsMutil(obj, fieldsA);
        List<List<Object>> argsB = getArgsMutil(obj, fieldsB);
        //拼接一次查询的参数
        List<Object> argsPerQuery = new ArrayList<>();
        //A行数
        int aRows = argsA.size();
        //B行数
        int bRows = argsB.size();
        //循环次数
        int qTimes = NumberUtil.max(aRows, bRows);
        for (int i = 0; i < qTimes; i++) {
            argsPerQuery = (List<Object>) CollectionUtil.addAll(argsA.get(NumberUtil.min(aRows - 1, i)), argsB.get(NumberUtil.min(bRows - 1, i)));
            String res = SqlExecutor.selectStr(sql, argsPerQuery.toArray());
            if (operator.equals(Operator.EQUALS)) {
                //sql结果与结果集的第一个参数匹配不上  则验证失败
                if (resultSet[0].equals(res)) {
                    continue;
                } else {
                    return false;
                }
            } else if (operator.equals(Operator.NOT_EQUALS)) {
                //sql结果与结果集的第一个参数匹配上  则验证失败
                if (resultSet[0].equals(res)) {
                    return false;
                } else {
                    continue;
                }
            } else {
                //TODO 其他逻辑操作符的相关判断逻辑
                return false;
            }
        }
        return true;
    }


    /**
     * 获取单个参数(Field的首层不是Collection接口的实现)
     *
     * @param obj    OBJ
     * @param fields Fieldsa
     * @return {@code List<Object>}
     */
    @Deprecated
    private List<Object> getArgsSingle(Object obj, String[] fields) {
        List<Object> subArgsItem = new ArrayList<>();
        /** 计算需要处理几个字段 */
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            String filed = StrUtil.removeAll(fields[i], "#");
            Object o = obj;
            //get方法队列
            Queue<String> mQueue = new LinkedList<String>();
            /*1.入队get方法*/
            Arrays.stream(filed.split("\\.")).forEach(s -> mQueue.add("get" + StrUtil.upperFirst(s)));
            /*2. 取值 反射 */
            Iterator<String> iterator = mQueue.iterator();
            while (iterator.hasNext()) {
                if (BeanUtil.isBean(o.getClass())) {
                    o = ReflectUtil.invoke(o, iterator.next());
                }
            }
            subArgsItem.add(o);
        }
        return subArgsItem;
    }

    /**
     * 获取多个参数(Field的首层是Collection接口的实现)
     *
     * @param obj    OBJ
     * @param fields 字段
     * @return {@code List<List<Object>>}
     */
    private List<List<Object>> getArgsMutil(Object obj, String[] fields) {
        List<List<Object>> subArgs = new ArrayList<>();
        /** 计算需要处理几个字段 */
        int length = fields.length;
        //get方法队列
        /**---------------构造需要调用的方法链--------------*/
        //比如#lines.name会先调用obj的getLines方法，得到对象obj1，再调用obj1的getName方法 以此类推 层层深入
        List<Queue<String>> methodChains = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            int finalI = i;
            Queue<String> methodChain = new LinkedList<>();
            String filed = StrUtil.removeAll(fields[finalI], "#");
            Arrays.stream(filed.split("\\.")).forEach(s -> methodChain.add("get" + StrUtil.upperFirst(s)));
            methodChains.add(i, methodChain);
        }
        /**---------------构造需要调用的方法链--------------*/

        Object invoke = ReflectUtil.invoke(obj, methodChains.get(0).peek());
        Collection collection;

        if (invoke instanceof Collection) {
            collection = (Collection) invoke;
        } else {
            collection = Arrays.asList(invoke);
        }

        int rows = CollectionUtil.size(collection);
        List<Object> rowArgs = new ArrayList<>();

        /** 反射获得一行行的参数 */
        for (int i = 0; i < rows; i++) {

            Object o = collection.iterator().next();
            Object o1 = o;
            for (int j = 0; j < methodChains.size(); j++) {
                /** 获取一行上面的若干个参数 */
                //循环调用方法链
                Queue<String> methodChain = methodChains.get(j);
                /*2. 取值 反射 */
                Iterator<String> iterator = methodChain.iterator();
                if (iterator.hasNext()) {
                    //去除lines本级
                    iterator.next();
                }
                while (iterator.hasNext() && BeanUtil.isBean(o.getClass())) {
                    //逐层取值 一直到底层 且必须是Bean才尝试取值 否则不一定有getter
                    o = ReflectUtil.invoke(o, iterator.next());
                }
                rowArgs.add(o);
                o = o1;
                /** 获取一行上面的若干个参数 */
            }
            subArgs.add(rowArgs);
            rowArgs = new ArrayList<>();
        }
        return subArgs;
    }


    /**
     * TB验证器
     * TB唯一验证器——唯一性校验
     *
     * @param schema        模式
     * @param tableName     表名
     * @param propertyNames 属性名称
     * @param realFieldName 字段名称
     * @param check         检查
     * @param obj           OBJ
     * @param appendSql     追加SQL
     * @return boolean
     */
    @Override
    public boolean tbValidator(TbCheck check, Object obj, String schema, String tableName, String[] propertyNames, String[] realFieldName, String appendSql) {
        log.info(TB_BEGIN_VALIDATE_NOTICE_INFO, tableName, check.getNote(), propertyNames);
        final boolean checkPass = paraChecker(propertyNames, realFieldName);
        Assert.isTrue(checkPass, TB_PARAMS_CHECK_ERR_MSG);
        if (check.equals(TbCheck.UNIQUE_CHECK)) {
            return fun10(obj, schema, tableName, propertyNames, realFieldName, appendSql);
        } else if (check.equals(TbCheck.EXIST_CHECK)) {
            return fun20(obj, schema, tableName, propertyNames, realFieldName, appendSql);
        }
        return false;
    }

    /**
     * Fun10 表校验 —— 唯一性校验
     *
     * @param obj           OBJ
     * @param schema        模式
     * @param tableName     表名
     * @param propertyNames 属性名称
     * @param realFieldName 实字段名
     * @return boolean
     */
    private boolean fun10(Object obj, String schema, String tableName, String[] propertyNames, String[] realFieldName, String appendSql) {
        Number sqlResult = funA10(obj, schema, tableName, propertyNames, realFieldName, appendSql);
        return NumberUtil.equals((BigDecimal) sqlResult, new BigDecimal(0));
    }

    /**
     * Fun20 表校验 —— 存在性校验
     *
     * @param obj           OBJ
     * @param schema        模式
     * @param tableName     表名
     * @param propertyNames 属性名称
     * @param realFieldName 实字段名
     * @return boolean
     */
    private boolean fun20(Object obj, String schema, String tableName, String[] propertyNames, String[] realFieldName, String appendSql) {
        Number sqlResult = funA10(obj, schema, tableName, propertyNames, realFieldName, appendSql);
        return NumberUtil.isGreater((BigDecimal) sqlResult, new BigDecimal(0));
    }

    private Number funA10(Object obj, String schema, String tableName, String[] propertyNames, String[] realFieldName, String appendSql) {
        final String sql = sqlBuilder(obj, schema, tableName, propertyNames, realFieldName, appendSql);
        List<Object> params = new ArrayList<>();
        Arrays.stream(propertyNames).forEach(i -> params.add(ReflectUtil.getFieldValue(obj, i)));
        Number number = SqlExecutor.selectNumber(sql, params.toArray());
        return ObjectUtil.isNull(number) ? 0 : number;
    }

    /**
     * 字段需要验证器
     *
     * @param target      目标
     * @param reference   参考文献
     * @param requireRule 需要规则
     * @param obj         OBJ
     * @param valueSet    值集
     * @return boolean
     */
    @Override
    public boolean fieldRequireValidator(Object obj, String target, String[] reference, RequireRule requireRule, String[] valueSet, String note) {
        return false;
    }

    /**
     * 类型校验器——Fun1 检查工单ID是否正确
     * 逻辑是 如果用户提供了任务令id 那就直接存，如果没有提供，那就必须提供一个切实存在的任务令号，根据任务令号找到id，找不到还是检验不通过
     *
     * @param obj        被校验的对象
     * @param fieldNames 字段名称
     * @return boolean
     */
    private boolean fun1(Object obj, String[] fieldNames) {
        final int argsNumbLimit = 3;
        final String fun1Sql = "SELECT we.wip_entity_id FROM wip.wip_entities we WHERE we.wip_entity_name = ? AND we.organization_id = ?";
        final List<String> targetMethodNames = Arrays.stream(fieldNames)
                .map(StrUtil::upperFirst)
                .map(fieldName -> "get" + fieldName)
                .collect(Collectors.toList());
        if (targetMethodNames.size() != argsNumbLimit) {
            log.error("验证失败，此规则下需要{}个参数", argsNumbLimit);
            return false;
        }
        final BigDecimal taskOrderId = ReflectUtil.invoke(obj, targetMethodNames.get(0));
        final String taskOrder = ReflectUtil.invoke(obj, targetMethodNames.get(1));
        final BigDecimal orgId = ReflectUtil.invoke(obj, targetMethodNames.get(2));
        /**
         * 1 若提供id 不为空 直接通过
         */
        if (ObjectUtil.isNotNull(taskOrderId)) {
            return true;
        }
        /**
         * 2 若提供任务令号，查验合法性
         */
        if (StringUtils.hasText(taskOrder)) {
            Number resNum = SqlExecutor.selectNumber(fun1Sql, taskOrder, orgId);
            return ObjectUtil.isNotEmpty(resNum);
        }
        return false;
    }

    /**
     * 类型校验器——Fun2 检查子库是否为VMI子库
     * apps.mtl_subinventories_all_v msav子库ATTRIBUTE3字段包含VMI；（则报错）
     *
     * @return boolean
     */
    private boolean fun2(Object obj, String[] fieldNames) {
        return false;
    }

    /**---------------------------------子校验逻辑结束 */


    /**---------------------------------功能函数开始 */

    /**
     * 唯一性校验器——参数检查器 sql要求的参数必须和提供的数目一致
     *
     * @return boolean
     */
    private boolean paraChecker(String[] propertyNames, String[] realFieldName) {
        if (ObjectUtil.isNotEmpty(realFieldName)) {
            return realFieldName.length == propertyNames.length;
        }
        return true;
    }


    /**
     * 唯一性校验器——SQL构建器
     *
     * @param obj OBJ
     * @return {@code String}
     */
    private String sqlBuilder(Object obj, String schema, String tableName, String[] propertyNames, String[] realFieldName, String appendSql) {
        StringBuilder sb = new StringBuilder("SELECT COUNT(1) FROM ");
        if (StringUtils.hasText(schema)) {
            sb.append(schema).append(".");
        }
        sb.append(tableName).append(" T WHERE 1=1 ");
        /** 判断是否指定了真实字段名 */
        boolean hasRealFieldName = ArrayUtil.isNotEmpty(realFieldName);
        List<String> propertyList = Arrays.stream(propertyNames).collect(Collectors.toList());
        int size = propertyList.size();
        for (int i = 0; i < size; i++) {
            /** 若指定了真实字段名称  则放弃转换下划线 取指定名称*/
            sb.append("AND T.").append(StrUtil.toUnderlineCase(hasRealFieldName ? realFieldName[i] : propertyList.get(i))).append(" = ? ");
        }
        if (StrUtil.isNotBlank(appendSql)) {
            sb.append("AND T.").append(appendSql.trim());
        }
        return sb.toString();
    }

    /**
     * 类型验证器——参数检查器
     * SQL中问号的数目必须和给的字段数一致
     *
     * @param sql     SQL
     * @param fieldsA Fieldsa
     * @param fieldsB 坠落
     * @return boolean
     */
    private boolean typeValidtorparaChecker(String sql, String[] fieldsA, String[] fieldsB) {
        /** provideNumberOfParameters */
        int pNum = fieldsA.length + fieldsB.length;
        /** declareNumberOfParameters*/
        int dNum = 0;
        char[] chars = sql.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '?') {
                dNum++;
            }
        }
        return pNum == dNum;
    }
}
