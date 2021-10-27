package site.heaven96.validate.iface.check.obj;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import site.heaven96.validate.common.enums.ValueSetOrigin;

/**
 * 对象检查
 *
 * @author lgw3488
 * @apiNote 对象检查抽象接口
 * @date 2021/10/27
 */
public abstract interface AbstractObjCheck<T, VSO extends ValueSetOrigin> {
    /**
     * 选择
     */
    String SELECT = "SELECT";

    /**
     * 井号
     */
    char POUND = '#';

    /**
     * 匹配值集来源
     *
     * @param val 合法值
     * @param vso 值集来源
     * @return {@code ValueSetOrigin}
     */
    default ValueSetOrigin matchVso(String val, final VSO vso) {
        //指定来源 优先级更高
        if (ObjectUtil.notEqual(vso, ValueSetOrigin.AUTO)) {
            return vso;
        }
        val = val.trim();
        if (val.charAt(0) == POUND && !StrUtil.equals(val, String.valueOf(POUND))) {
            //#开头 但不仅仅只有# 判断为属性值
            return ValueSetOrigin.DYNAMIC_SPECIFIED_VALUE;
        }
        if (!StrUtil.equalsIgnoreCase(val, SELECT) && StrUtil.startWithIgnoreCase(val, SELECT)) {
            //SELECT 开头但不仅仅只有SELECT这个一个单词 判断为SQL
            return ValueSetOrigin.SQL_RESULTS;
        }
        return ValueSetOrigin.FIXED_VALUE;
    }


    /**
     * 匹配值集来源
     *
     * @param valSet 合法值集
     * @param vso    值集来源
     * @return {@code ValueSetOrigin}
     */
    default ValueSetOrigin matchVso(final String[] valSet, final VSO vso) {
        //指定来源 优先级更高
        if (ObjectUtil.notEqual(vso, ValueSetOrigin.AUTO)) {
            return vso;
        }
        //自动判断
        final int size = ArrayUtil.length(valSet);
        if (size != 1) {
            //值集为空或者大于一个值 为固定值判断模式
            return ValueSetOrigin.FIXED_VALUE;
        }
        final String val = ArrayUtil.isEmpty(valSet) ? StrUtil.EMPTY : ArrayUtil.get(valSet, 0);
        return matchVso(val, vso);
    }

    /**
     * 获取合法值集
     * 合法值集可能直接给定，可能是运算出来或者外部取的，
     * 先取得String[]再把Class和Object（被比较对象）看齐，获得Object[]
     *
     * @return {@code Object[]}
     */
    default Object[] getValSet(T obj, String[] valSet, final VSO vso) {
        ValueSetOrigin valueSetOrigin = matchVso(valSet, vso);
        if (ValueSetOrigin.FIXED_VALUE != valueSetOrigin) {
            //需要动态计算
        }
        //不需要动态计算

        return null;
        //TODO now
    }

    /**
     * 检查
     *
     * @param obj   被检查对象
     * @param value 合法值
     * @return boolean
     * @deprecated 不确定是否有用  合理
     */
    @Deprecated
    boolean check(T obj, String value);

}
