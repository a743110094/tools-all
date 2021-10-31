package site.heaven96.validate.imp.check.field;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import site.heaven96.assertes.common.exception.H4nBeforeValidateCheckException;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.annotation.H4nCheck;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.util.H4nAnalysisUtil;
import site.heaven96.validate.util.H4nCompareUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工会提出检查impl
 *
 * @author heaven96
 * @date 2021/10/30 22:34:23
 */
public class UnionFiledCheckerImpl extends AbFieldChecker<H4nCheck> {

    /**
     * 检查
     * 计划平替 {@link site.heaven96.validate.service.impl.FieldCheckServiceImpl }
     *
     * @see site.heaven96.validate.service.impl.FieldCheckServiceImpl
     * @param obj        待检查值
     * @param logic      逻辑
     * @param ignoreCase 忽略大小写
     * @param legals     合法值集
     * @return boolean
     */
    @Override
    public boolean check(Object obj, Logic logic, boolean ignoreCase, Object legals) {
        boolean legalsIsColl = legals instanceof Collection;
        boolean legalsIsArray = null != legals && legals.getClass().isArray();
        if (legalsIsArray){
            return check(obj, logic, ignoreCase, (Object[]) legals);
        }
        if (legalsIsColl){
            Object[] legalsArray = ((Collection) legals).toArray();
            return check(obj, logic, ignoreCase,legalsArray);
        }
        return  false;
    }

    private boolean check(Object obj, Logic logic, boolean ignoreCase, Object[] legals) {
        return false;
    }


}
