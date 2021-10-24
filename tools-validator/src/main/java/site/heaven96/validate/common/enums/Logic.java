package site.heaven96.validate.common.enums;

/**
 * logic
 *
 * @author Heaven96
 * @date 2021/10/13
 */
public enum Logic {
    /**
     * 无
     */
    NONE("空条件，默认忽略此逻辑", 0),
    /**
     * 如果
     */
    IF("如果条件", 1),
    /**
     * 并列 如果
     */
    AND_IF("并列 如果条件", 2),
    /**
     * 或者
     */
    OR_IF("或者条件", 3),
    /**
     * 然后
     */
    THEN("然后",  - 3),
    /**
     * 并列 然后
     */
    AND_THEN("然后",  - 2),
    /**
     * 或者然后
     */
    OR_THEN("然后", - 1);


    /**
     * 描述
     */
    private final String note;
    /**
     * 顺序 条件类必须大于0 结论类必须小于0
     */
    private final int order;


    /**
     * 逻辑运算符
     *
     * @param note 描述
     */
    Logic(String note, int order) {
        this.note = note;
        this.order = order;
    }

    public int getOrder() {
        return order;
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
