package site.heaven96.validate.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.enums.ValueSetOrigin;

/**
 * 自动选择实用程序
 *
 * @author lgw3488
 * @date 2021/10/22
 */
public class AutoChooseUtil{
    /**
     * 选择
     */
    private static final String SELECT = "SELECT";

    /**
     * 井号
     */
    private static final char POUND = '#';

    /**
     * 自动根据值集的风格 判断值集来源
     *
     * @param vso 值集来源
     * @param vs  值集
     */
    public static ValueSetOrigin valueSetOrigin(ValueSetOrigin vso, String[] vs) {
        //默认自动判断 但指定的优先级更高
        if (ObjectUtil.notEqual(vso, ValueSetOrigin.AUTO)) {
            return vso;
        }
        //Auto choose vs Origin
        final int size = ArrayUtil.length(vs);
        String trim = ArrayUtil.isEmpty(vs) ? "" : vs[0].trim();
        final int length = StrUtil.length(trim);
        if (size == 0 || size > 1) {
            //值集为空或者大于一个值 为固定值判断模式
            return ValueSetOrigin.FIXED_VALUE;
        } else {
            if (length > 1 && trim.charAt(0) == POUND ) {
                //#开头 但不仅仅只有# 为取其他属性值
                return ValueSetOrigin.DYNAMIC_SPECIFIED_VALUE;
            }
            if (length > 7 && StrUtil.startWithIgnoreCase(trim, SELECT)) {
                //SELECT 开头但不仅仅只有这个一个单词 取SQL
                return ValueSetOrigin.SQL_RESULTS;
            }
        }
        return ValueSetOrigin.FIXED_VALUE;
    }
}
