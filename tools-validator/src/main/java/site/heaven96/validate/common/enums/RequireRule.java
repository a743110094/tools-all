package site.heaven96.validate.common.enums;

/**
 * 需要规则
 * 必填校验规则
 *
 * @author lgw3488
 * @date 2021/10/10
 */
public enum RequireRule {
    /**
     * 参考字段全空则目标字段必填
     */
    WHEN_REF_ALL_NULL_THEN_REQUIRE_TARGET("参考字段为空，目标字段必填"),
    /**
     * 参考字段存在空值则目标字段必填
     */
    //TODO 开发此功能
    WHEN_REF_HAS_NULL_THEN_REQUIRE_TARGET("参考字段存在空值，目标字段必填，这个空值包括null，空字符串已经没有意义的诸如换行符，制表符"),
    /**
     * 任一参考字段存在于值集则目标字段必填
     */
    ANY_REF_IN_VALUE_SET_THEN_REQUIRE_TARGET("参考字段为指定值时，目标字段必填"),
    /**
     * 目标必须在值集中
     */
    TARGET_MUST_IN_VALUE_SET("目标字段必须在给定的值域中");

    /**
     * 简介
     *
     * @return {@code String}
     */
    String intro;


    RequireRule(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
