package site.heaven96.assertes.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.assertes.common.exception.H4nBeforeValidateCheckException;
import site.heaven96.assertes.common.exception.H4nUnExpectedException;


/**
 * 断言工具
 *
 * @author Heaven96
 * @date 2021/10/18
 */
@Slf4j
public class AssertUtil {
    //----------------------------------------------------Public Method
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
            String err = StrUtil.format(errMsg.trim(), errMsgParams);
            //log.error(err);
            throw new H4nBeforeValidateCheckException(err);
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

    /**
     * 必须为真
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isTrue(final boolean flag, final String errMsg) {
        if (!flag) {
            log.error(errMsg.trim());
            throw new H4nUnExpectedException(errMsg.trim());
        }
    }


    /**
     * 必须为真
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isTrue(final boolean flag, final String errMsg, Object... errMsgParams) {
        if (!flag) {
            String err = StrUtil.format(errMsg.trim(), errMsgParams);
            throw new H4nUnExpectedException(err);
        }
    }

    /**
     * 必须为假
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isFalse(final boolean flag, final String errMsg) {
        isTrue(!flag, errMsg);
    }

    /**
     * 必须为假
     *
     * @param flag   标识
     * @param errMsg 错误消息
     */
    public static void isFalse(final boolean flag, final String errMsg, Object... errMsgParams) {
        isTrue(!flag, errMsg, errMsgParams);
    }

    //----------------------------------------------------Public Method

}
