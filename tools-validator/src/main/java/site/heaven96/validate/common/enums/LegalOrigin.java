package site.heaven96.validate.common.enums;

/**
 * 合法值（集）来源
 *
 * @author Heaven96
 * @date 2021/10/11
 */
public enum LegalOrigin {
    /**
     * 自动
     *
     * @since 0.0.2
     */
    AUTO,
    /**
     * 固定值 写在valueSet注解中
     */
    FIXED,
    /**
     * 动态指定值 接口传入
     *
     * @since 0.0.2
     * //TODO 可以实现  推后
     */
    DYNAMIC,
    /**
     * sql执行结果 根据给定的sql执行结果
     *
     * @since 0.0.2
     */
    SQL,
    /**
     * 反射调用方法 <br/>新增 @2021年10月29日 22:32:04<br/>
     * 用于实现更加复杂的验证逻辑<br/>
     * 值集填包和方法，例如：<br/>
     * legalOrigin="com.mypackage.classA.functionB"<br/>
     * 要求functionB的入参就是注解所在的元素，执行校验时作为入参......
     *
     * @since 0.0.3
     */
    REFLECT
}
