package site.heaven96.validate.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.common.exception.H4nBeforeValidateCheckException;


/**
 * 断言工具
 *
 * @author Heaven96
 * @date 2021/10/18
 */
@Slf4j
public class AssertUtil {
    /**
     * 必须为真  否则在抛出H4N预检验失败异常
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isTrueThrowBeforeExp(final boolean flag, final String errMsg) {
        if (!flag) {
            log.error(errMsg.trim());
            throw new H4nBeforeValidateCheckException(errMsg.trim());
        }
    }


    /**
     * 必须为真  否则在抛出H4N预检验失败异常
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isTrueThrowBeforeExp(final boolean flag, final String errMsg, Object... errMsgParams) {
        if (!flag) {
            log.error(errMsg.trim());
            throw new H4nBeforeValidateCheckException(StrUtil.format(errMsg.trim(), errMsgParams));
        }
    }


    ///////////////////////////////////////


    /**
     * 必须为假  否则在抛出H4N预检验失败异常
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isFalseThrowBeforeExp(final boolean flag, final String errMsg) {
        isTrueThrowBeforeExp(!flag, errMsg);
    }

    /**
     * 必须为假  否则在抛出H4N预检验失败异常
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isFalseThrowBeforeExp(final boolean flag, final String errMsg, Object... errMsgParams) {
        isTrueThrowBeforeExp(!flag, errMsg, errMsgParams);
    }
}
