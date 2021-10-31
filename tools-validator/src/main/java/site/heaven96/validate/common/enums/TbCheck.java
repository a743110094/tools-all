package site.heaven96.validate.common.enums;

/**
 * TB检查
 *
 * @author Heaven96
 * @date 2021/10/08
 */
public enum TbCheck {
    /**
     * 唯一性约束
     */
    PRIMARY_KEY_CHECK("唯一性约束(主键约束)"),
    /**
     * 存在性约束
     */
    EXIST_CHECK("存在性约束"),
    /**
     * 存在,只
     */
    EXISTS_AND_UNIQUE("存在且唯一")
    ;

    private String note;

    TbCheck(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
