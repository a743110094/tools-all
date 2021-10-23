package site.heaven96.validate.common.enums;

/**
 * 运算符
 *
 * @author Heaven96
 * @apiNote 用作结果判断的逻辑运算
 * @date 2021/10/08
 */
public enum Operator {
    AUTO("自动"),
    /**
     * 等于 .
     */
    EQUALS("SQL执行结果与给定结果集的第一个值匹配"),
    /**
     * 不等于 .
     */
    NOT_EQUALS("SQL执行结果与给定结果集的第一个值不匹配"),
    /**
     * 在其中
     */
    IN("SQL执行结果存在于结果集"),
    /**
     * 不在其中
     */
    NOT_IN("SQL执行结果不在结果集"),
    /**
     * 大于
     */
    GREATER_THAN("SQL执行结果必须是数字，大于结果集第一个值，若执行返回null，默认Integer最小值"),
    /**
     * 大于等于
     */
    GREATER_THAN_OR_EQUALS("SQL执行结果必须是数字，大于等于结果集第一个值，若执行返回null，默认Integer最小值"),
    /**
     * 小于
     */
    LESS_THAN("SQL执行结果必须是数字/日期，小于结果集第一个非空值，若执行ValueSet为null，默认Integer最大值"),
    /**
     * 小于等于
     */
    LESS_THAN_OR_EQUAL_TO("SQL执行结果必须是数字/日期，小于等于结果集第一个非空值，若执行ValueSet为null，默认Integer最大值"),
    /**
     * 在范围内 通过valueSet表示区间开  闭 .
     */
    BETWEEN_AND("在范围内"),
    /**
     * 为空 .
     */
    IS_NULL("为空"),
    /**
     * 非空 .
     */
    NOT_NULL("非空"),
    /**
     * 具有文本 .
     */
    HAS_TEXT("具有文本"),
    /**
     * 没有文本 .
     */
    HAS_NO_TEXT("没有文本");

    /**
     * 描述
     */
    private final String note;

    /**
     * 逻辑运算符
     *
     * @param note 描述
     */
    Operator(String note) {
        this.note = note;
    }

    /**
     * 获取描述
     *
     * @return {@code String}
     */
    public String getNote() {
        return note;
    }
}
