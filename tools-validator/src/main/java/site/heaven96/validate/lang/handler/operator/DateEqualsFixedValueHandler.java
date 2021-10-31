package site.heaven96.validate.lang.handler.operator;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.util.H4nDateUtil;
import site.heaven96.validate.util.H4nCompareUtil;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;


/**
 * 日期等于处理程序
 *
 * @author Heaven96
 * @date 2021/10/20
 */
public class DateEqualsFixedValueHandler extends AbstractEqualsFixedValueHandler {

    @Override
    public boolean subHandle(Object obj, Logic logic, Object standardVal) {
        //这里已经是 抽象类 AbstractEqualsFixedValueHandler 的子类 无需判断 logic 肯定  equals
        ///////////集合判定
        boolean objIsColl = obj instanceof Collection;
        if (objIsColl){
            return subHandleForColl((Collection<Object>) obj,logic,standardVal);
        }
        /////////////////////////分割  下面为非集合判定
        boolean objIsDate = H4nDateUtil.isDate(obj);
        if (!objIsDate) {
            AssertUtil.isTrueThrowBeforeExp(nextEqualsHandler() != null, AE_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextEqualsHandler().subHandle(obj, logic, standardVal);
        }
        String stStr = StrUtil.str(standardVal, StandardCharsets.UTF_8);
        Date date2 = H4nDateUtil.toDate(stStr);
        //这里先判断可以转然后再转有些多余 TODO
        AssertUtil.isTrueThrowBeforeExp(ObjectUtil.isNotNull(date2), "\n===>进行日期比较时，值[{}]不能被转换为合法的日期", stStr);
        return H4nCompareUtil.equals(obj, H4nDateUtil.toDate(stStr));
    }

    private boolean subHandleForColl(Collection<Object> obj, Logic logic, @NotNull Object standardVal){
        boolean objAreAllDate = obj.stream().allMatch(o -> H4nDateUtil.isDate(o));
        if (!objAreAllDate) {
            AssertUtil.isTrueThrowBeforeExp(nextEqualsHandler() != null, AE_HANDLER_NOT_MATCHES_ERR_MSG);
            return nextEqualsHandler().subHandle(obj, logic, standardVal);
        }
        String stStr = StrUtil.str(standardVal, StandardCharsets.UTF_8);
        Date date2 = H4nDateUtil.toDate(stStr);
        return obj.stream().allMatch(o->{
            AssertUtil.isTrueThrowBeforeExp(ObjectUtil.isNotNull(date2), "\n===>进行日期比较时，值[{}]不能被转换为合法的日期", stStr);
            return H4nCompareUtil.equals(o, H4nDateUtil.toDate(stStr));
        });
    }
}
