package site.heaven96.validate.imp.check.obj;


import site.heaven96.validate.common.annotation.H4nUnionCheck;


/**
 * 联合检查器实施
 *
 * @author lgw3488
 * @date 2021/10/29
 */
public class UnionCheckerImpl extends AbObjectChecker<H4nUnionCheck> {
    /**
     * 检查
     *
     * @param annotation   注解
     * @param beCheckedObj 被检查对象
     * @param legal        合法值（集）
     * @return boolean
     */
    @Override
    public boolean check(H4nUnionCheck annotation, Object beCheckedObj, Object[] legal) {
        return false;
    }
}
