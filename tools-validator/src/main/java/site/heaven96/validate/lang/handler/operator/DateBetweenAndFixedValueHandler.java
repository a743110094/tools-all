package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.ArrayUtil;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Operator;

import javax.validation.constraints.NotNull;

/**
 * 数字 介于 处理程序
 *
 * @author Heaven96
 * @date 2021/10/19
 */
//TODO 开发  目前未开发
public class DateBetweenAndFixedValueHandler extends AbstractBetweenAndFixedValueHandler {


    /**
     * 数字 介于 处理程序 obj 必须介于值集第一个非空元素表达式 表达的值域范围 之间
     * 例如：
     * obj：1  valueSet {"","[0,1]"} 是合法的
     *
     * @param obj      OBJ
     * @param operator 运算符
     * @param valueSet 值集
     * @return boolean
     */
    @Override
    public boolean subHandle(Object obj, Operator operator, @NotNull Object[] valueSet) {
        boolean objIsDate = site.heaven96.validate.util.DateUtil.isDate(obj);
        if (!objIsDate) {
            AssertUtil.isTrueThrowBeforeExp(nextBetweenAndHandler()!=null,BA_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextBetweenAndHandler().subHandle(obj, operator, valueSet);
        }
        if (ArrayUtil.isEmpty(valueSet)){
            AssertUtil.isTrueThrowBeforeExp(nextBetweenAndHandler()!=null,VALUE_SET_IS_NULL_ERR_MSG);
        }
        return false;
    }
}
