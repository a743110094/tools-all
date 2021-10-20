package site.heaven96.validate.util;

import site.heaven96.validate.lang.handler.compare.*;

/**
 * 超级比较工具
 *
 * @author lgw3488
 * @date 2021/10/19
 */
public class H4nCompareUtil {

    private static AbstractCompareHandler getComparator() {
        AbstractCompareHandler numberCompareHandler = new NumberCompareHandler();
        AbstractCompareHandler dateCompareHandler = new DateCompareHandler();
        AbstractCompareHandler objectCompareHandler = new ObjectCompareHandler();
        AbstractCompareHandler stringCompareHandler = new StringCompareHandler();
        numberCompareHandler.setNext(dateCompareHandler);
        dateCompareHandler.setNext(stringCompareHandler);
        stringCompareHandler.setNext(objectCompareHandler);
        return numberCompareHandler;
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
        return getComparator().handle(o1, o2, false) <= 0;
    }


}
