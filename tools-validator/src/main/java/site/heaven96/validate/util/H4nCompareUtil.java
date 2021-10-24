package site.heaven96.validate.util;

import cn.hutool.core.util.ObjectUtil;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.lang.handler.base.compare.*;

/**
 * 超级比较工具
 *
 * @author Heaven96
 * @date 2021/10/19
 */
public class H4nCompareUtil {

    private static AbstractCompareHandler getComparator() {
        //数字比较器
        AbstractCompareHandler numberCompareHandler = new NumberCompareHandler();
        //日期
        AbstractCompareHandler dateCompareHandler = new DateCompareHandler();
        //字符串
        AbstractCompareHandler stringCompareHandler = new StringCompareHandler();
        //对象
        AbstractCompareHandler objectCompareHandler = new ObjectCompareHandler();

        numberCompareHandler.setNext(dateCompareHandler);
        dateCompareHandler.setNext(stringCompareHandler);
        stringCompareHandler.setNext(objectCompareHandler);
        return numberCompareHandler;
    }


    /**
     * 等于 传入是否忽略大小写参数
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean equalsB(Object o1, Object o2,boolean ignoreCase){
       return getComparator().handle(o1, o2, ignoreCase) == 0;
    }


    /**
     * 等于
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean equals(Object o1, Object o2) {
        return getComparator().handle(o1, o2, false) == 0;
    }

    /**
     * 不等于
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean notEquals(Object o1, Object o2) {
        return !equals(o1, o2);
    }

    /**
     * 等于忽略大小写
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean equalsIgnoreCase(Object o1, Object o2) {
        return getComparator().handle(o1, o2, true) == 0;
    }


    /**
     * 是更大的
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean isGreater(Object o1, Object o2) {
        if (ObjectUtil.isNull(o1)){
            return false;
        }
        AssertUtil.isTrueThrowBeforeExp(ComparableUtil.rangeCompare(o1),"范围比较仅适用于数字或者日期，您提供的值[{}]属于[{}]",o1,o1.getClass());
        return getComparator().handle(o1, o2, false) > 0;
    }


    /**
     * 大于或等于
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean greaterOrEquals(Object o1, Object o2) {
        if (ObjectUtil.isNull(o1)){
            return false;
        }
        AssertUtil.isTrueThrowBeforeExp(ComparableUtil.rangeCompare(o1),"范围比较仅适用于数字或者日期，您提供的值[{}]属于[{}]",o1,o1.getClass());
        return getComparator().handle(o1, o2, false) >= 0;
    }

    /**
     * 更少
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean isLess(Object o1, Object o2) {
        if (ObjectUtil.isNull(o1)){
            return false;
        }
        AssertUtil.isTrueThrowBeforeExp(ComparableUtil.rangeCompare(o1),"范围比较仅适用于数字或者日期，您提供的值[{}]属于[{}]",o1,o1.getClass());
        return getComparator().handle(o1, o2, false) < 0;
    }


    /**
     * 小于或等于
     *
     * @param o1 1.
     * @param o2 氧气
     * @return boolean
     */
    public static boolean lessOrEquals(Object o1, Object o2) {
        if (ObjectUtil.isNull(o1)){
            return false;
        }
        AssertUtil.isTrueThrowBeforeExp(ComparableUtil.rangeCompare(o1),"范围比较仅适用于数字或者日期，您提供的值[{}]属于[{}]",o1,o1.getClass());
        return getComparator().handle(o1, o2, false) <= 0;
    }

}
