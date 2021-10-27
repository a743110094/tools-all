package site.heaven96.validate.util;

/**
 * 可比实用程序
 *
 * @author Heaven96
 * @date 2021/10/24
 */
public class ComparableUtil {

    /**
     * 范围比较
     * @apiNote 判断该对象是否支持值域类的比较(BETWEEN_AND,GREATER,LESS...)
     *          此处的值域比较是指范围内 不是指在某个选项范围内（IN）
     *          类似于连续函数上找一个点和有限点集上找一个点的区别
     *          支持这种比较的只有日期和数字
     * @return boolean
     * @since 0.0.8
     */
    public static boolean rangeCompare(Object obj){
        return DateUtil.isDate(obj) || obj instanceof Number;
    }
}
