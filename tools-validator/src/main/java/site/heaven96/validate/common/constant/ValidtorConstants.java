package site.heaven96.validate.common.constant;

/**
 * 验证器统一封装常量
 *
 * @author lgw3488
 * @date 2021/10/10
 */
public class ValidtorConstants {

    //------------------------------  本地必输校验
    /**
     * 入口日志
     */
    public static final String REQ_DEFAULT_ENTRANCE_LOG = "开始字段必输校验\n [校验规则] -> {} \n [备注] -> {}";
    public static final String REQ_NO_RULE_FOUND_ERR_MSG = "未找到验证规则";
    public static final String REQ_NO_GETTER_ERR_MSG = "无法完成必填校验——>对象无Getter方法";
    //------------------------------  本地必输校验


    //------------------------------  类 校验器
    public static final String TYPE_BEGIN_VALIDATE_NOTICE_INFO = "\n===> 开始验证,验证规则：[{}]";
    //------------------------------  类 校验器

    //------------------------------  表 校验器
    public static final String TB_BEGIN_VALIDATE_NOTICE_INFO = "\n===> 开始进行表[{}]的[{}]校验，涉及字段[{}]";

    public static final String TB_PARAMS_CHECK_ERR_MSG = "\n===> 提供的字段数和字段别名数不一致，无法开始验证";
    //------------------------------  表 校验器
}
