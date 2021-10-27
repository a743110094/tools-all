package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import site.heaven96.assertes.common.exception.H4nBeforeValidateCheckException;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.util.DateUtil;

import javax.validation.constraints.NotNull;

/**
 * 数字等于处理程序
 *
 * @author Heaven96
 * @date 2021/10/20
 */
public class InFixedValueHandler extends AbstractFixedValueHandler {


    /**
     * 参数类型非法 错误消息
     */
    private static final String ILLEGAL_PARAMETERS_TYPE_ERR_MSG = "Operator为In时，只允许校验日期、数字、字符串";

    /**
     * 处理请求
     *
     * @param obj      OBJ
     * @param logic    运算符
     * @param valueSet 值集
     * @return boolean
     * @apiNote 在某个给定直接内 可以是字符串 数字 日期
     * 如：1是否在{1,2,3,4,5}内
     * "admin"是否在{"admin","a"}内
     */
    @Override
    public boolean handle(Object obj, Logic logic, @NotNull Object[] valueSet) {
        if (ObjectUtil.notEqual(logic, Logic.IN)) {
            //不是In类的比较 传递给责任链的下一环
            AssertUtil.isTrueThrowBeforeExp(ObjectUtil.isNotNull(getNext()), FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj, logic, valueSet);
        }
        if (ArrayUtil.isEmpty(valueSet)) {
            //值集为空直接false
            return false;
        }
        if (obj instanceof Number || obj instanceof String || DateUtil.isDate(obj)) {
            //只针对部分数据类型有效
            if (obj instanceof Number){

            }
        }else{
            throw new H4nBeforeValidateCheckException(ILLEGAL_PARAMETERS_TYPE_ERR_MSG);
        }
        return false;
    }
}
