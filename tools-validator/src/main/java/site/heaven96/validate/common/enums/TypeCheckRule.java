package site.heaven96.validate.common.enums;

/**
 * 单个字段校验验证规则
 *
 * @author Heaven96
 * @date 2021/10/10
 */
public enum TypeCheckRule {
    /**
     * 检查库存组织代码是否正确 存在性验证
     */
    检查库存组织代码是否正确_存在性验证,
    /**
     * 检查工单 id是否正确
     */
    检查工单ID是否正确,
    /**
     * 检查子库是否为 vmi子库
     */
    检查子库是否为VMI子库,

    /**
     * 在值集中
     */
    VALUE_SET,

    /**
     * 自定义
     */
    自定义
}
