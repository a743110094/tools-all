package site.heaven96.validate.common.enums;

/**
 * 值集原点
 *
 * @author lgw3488
 * @date 2021/10/11
 */
public enum ValueSetOrigin {
    /**
     * 自动
     */
    AUTO,
    /**
     * 固定值 写在valueSet注解中
     */
    FIXED_VALUE,
    /**
     * 动态指定值 接口传入
     * //TODO 可以实现  推后
     */
    DYNAMIC_SPECIFIED_VALUE,
    /**
     * sql执行结果 根据给定的sql执行结果
     */
    SQL_RESULTS;
}
