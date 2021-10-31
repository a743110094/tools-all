package site.heaven96.validate.imp.check.obj;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.iface.check.obj.ObjectChecker;
import site.heaven96.validate.util.SpelUtil;
import site.heaven96.validate.util.SqlExecutor;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 抽象对象检查器
 *
 * @author lgw3488
 * @date 2021/10/29
 */
public abstract class AbObjectChecker<A extends Annotation> implements ObjectChecker<A> {
    //----------------------------------- Public object
    /**
     * 来源
     */
    private LegalOrigin origin;

    //-----------------------------------

    /**
     * <b>STEP 1</b><br>
     * 获取合法值集 <br>
     * <li> 固定值：直接返回
     * <li> 动态值、SQL、反射方法：计算后返回
     *
     * @param obj         被检查对象 此处就指的是@H4nCheck、@H4nTbCheck等注解的对象
     * @param legalOrigin 法律渊源
     * @param legal       法律上的
     * @return {@code Object[]}
     */
    public Object getLegals(LegalOrigin legalOrigin, @NotNull String[] legal, Object obj) {
        //Calculate the legal origin
        origin = LegalOrigin.AUTO.equals(legalOrigin) ? this.getLegalOrigin(legalOrigin, legal) : legalOrigin;
        //Calculate the legal value(s)
        final String firstLegal = legal[0];
        switch (origin) {
            case FIXED: {
                return Arrays.stream(legal).collect(Collectors.toList());
            }
            case SQL: {
                //
                return SqlExecutor.selectStrs(firstLegal, null);
            }
            case DYNAMIC: {
                //动态指定值 接口传入
                return SpelUtil.get(firstLegal, obj);
            }
            case REFLECT: {
                //反射调用方法

            }
            default: {
                return null;
            }
        }
    }


}
