package site.heaven96.validate.iface.check.obj;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.enums.LegalOrigin;

import java.lang.annotation.Annotation;
import java.util.Objects;

/**
 * 对象检查
 *
 * @author lgw3488
 * @apiNote 对象检查抽象接口
 * @date 2021/10/27
 */
public interface ObjectChecker<A extends Annotation> {

    /**
     * 选择
     */
    String SELECT = "SELECT";

    /**
     * 井号
     */
    char POUND = '#';

    /**
     * 获取值集来源
     *
     * @param legal  合法值集
     * @param origin 合法值集来源
     * @return {@code LegalOrigin}
     */
    default LegalOrigin getValSetOrigin(LegalOrigin origin, String[] legal) {
        //指定来源 优先级更高
        if (ObjectUtil.notEqual(origin, LegalOrigin.AUTO)) {
            return origin;
        }
        //自动判断
        if (ArrayUtil.length(legal) != 1 || Objects.isNull(ArrayUtil.get(legal, 0))) {
            //值集为空或者大于一个值 或者有且仅有一个null 为固定值判断模式
            return LegalOrigin.FIXED;
        }
        final String firstLegal = ArrayUtil.get(legal, 0);
        if (firstLegal.charAt(0) == POUND && !StrUtil.equals(firstLegal, String.valueOf(POUND))) {
            //#开头 但不仅仅只有# 判断为动态属性值
            return LegalOrigin.DYNAMIC;
        }
        if (!StrUtil.equalsIgnoreCase(firstLegal, SELECT) && StrUtil.startWithIgnoreCase(firstLegal, SELECT)) {
            //SELECT 开头但不仅仅只有SELECT这个一个单词 判断为SQL
            return LegalOrigin.SQL;
        }
        return LegalOrigin.FIXED;
    }

    /**
     * 检查
     *
     * @param beCheckedObj 被检查对象
     * @param legal        合法值（集）
     * @param annotation   注解
     * @return boolean
     */
    boolean check(A annotation, Object beCheckedObj, Object[] legal);
}
