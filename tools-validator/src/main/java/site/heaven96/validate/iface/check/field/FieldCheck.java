package site.heaven96.validate.iface.check.field;


import site.heaven96.validate.common.enums.Logic;

/**
 * 字段检查接口 (实现需要区分JDK8和JDK8以前) <br>
 * 需要提供 <li>被检查对象 <li>检查逻辑 <li>合法值（集） <br> 返回检查结果 <br>
 * 例如可检查包括但不仅限于以下的例子：
 * <li>1 大于 0 ? -> false
 * <li>2 介于 [0,2) ? -> false
 * <li>"3" 在 {"2","4"} 范围内 ? -> false
 * <li>"2020-01-01" 晚于 "2020-01-25" ? -> false
 * <li>"广州" 在 "SELECT 今天下雨的城市 FROM 数据库" -> 依据sql执行的结果判断
 * <li>"成都" 作为入参调用  boolean isSunny(String cityName); 方法后判断是否为晴天
 *
 * @author Heaven96
 * @date 2021/10/26
 */
public interface FieldCheck<T, L extends Logic> {
    /**
     * 是否缓存字段检验结果（建议大对象，外部调用，反射调用方法都缓存）
     */
    boolean CACHE_RES = false;


    /**
     * 自动匹配逻辑运算关系符
     *
     * @param logic  逻辑
     * @param valSet VAL集
     * @return {@code Logic}
     */
    default Logic matchLogic(L logic, String[] valSet) {
        //TODO 根据值域的风格尝试匹配逻辑
        return Logic.EQUALS;
    }

    /**
     * 匹配逻辑
     * 自动匹配逻辑运算关系符
     *
     * @param logic 逻辑
     * @param val   VAL
     * @return {@code Logic}
     */
    default Logic matchLogic(L logic, String val) {
        //TODO 根据值域的风格尝试匹配逻辑
        return null;
    }

    /**
     * 自动匹配逻辑运算关系符
     *
     * @param valSet VAL集
     */
    default Logic matchLogic(String[] valSet) {
        //TODO 根据值域的风格尝试匹配逻辑
        return null;
    }

    /**
     * 启用缓存
     *
     * @param enable 启用
     */
    default void enableCache(boolean enable) {
    }

    /**
     * 检查
     *
     * @param obj    OBJ
     * @param logic  逻辑
     * @param valSet VAL集
     * @return boolean
     */
    boolean check(T obj, L logic, String[] valSet);

    /**
     * 检查
     *
     * @param obj   OBJ
     * @param logic 逻辑
     * @param val   VAL
     * @return boolean
     */
    boolean check(T obj, L logic, String val);

    /**
     * 高速缓存
     * 缓存
     *
     * @param obj    OBJ
     * @param logic  逻辑
     * @param valSet VAL集
     * @param result 结果
     */
    void cache(T obj, L logic, String[] valSet, boolean result);

    /**
     * 高速缓存
     * 缓存
     *
     * @param obj    OBJ
     * @param logic  逻辑
     * @param val    VAL
     * @param result 结果
     */
    void cache(T obj, L logic, String val, boolean result);

    /**
     * 已缓存
     * 是否缓存
     *
     * @param obj    OBJ
     * @param logic  逻辑
     * @param valSet VAL集
     * @param result 结果
     * @return boolean
     */
    boolean isCached(T obj, L logic, String[] valSet, boolean result);

    /**
     * 已缓存
     * 是否缓存
     *
     * @param obj    OBJ
     * @param logic  逻辑
     * @param val    VAL
     * @param result 结果
     * @return boolean
     */
    boolean isCached(T obj, L logic, String val, boolean result);

}
