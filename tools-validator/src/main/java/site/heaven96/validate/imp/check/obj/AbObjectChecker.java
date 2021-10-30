package site.heaven96.validate.imp.check.obj;

import site.heaven96.validate.common.enums.LegalOrigin;
import site.heaven96.validate.iface.check.obj.ObjectChecker;

import java.lang.annotation.Annotation;

/**
 * 抽象对象检查器
 *
 * @author lgw3488
 * @date 2021/10/29
 */
public abstract class AbObjectChecker<A extends Annotation> implements ObjectChecker<A> {
    //----------------------------------- Private object
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
     * @param obj         被检查对象
     * @param legalOrigin 法律渊源
     * @param legal       法律上的
     * @return {@code Object[]}
     */
    private Object[] getValSet(LegalOrigin legalOrigin, String[] legal, Object obj) {
        if (LegalOrigin.AUTO.equals(legalOrigin)) {
            //Calculate the legal origin
            origin = this.getValSetOrigin(legalOrigin, legal);
        }
        switch (legalOrigin) {
            case FIXED: {
            }
            case SQL: {
            }
            case DYNAMIC: {
            }
            case REFLECT: {
            }
            default: {
                return null;
            }
        }
    }


}
