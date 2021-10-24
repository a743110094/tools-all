package site.heaven96.validate.lang.handler.base.compare;

import cn.hutool.core.util.StrUtil;
import site.heaven96.assertes.common.exception.H4nBeforeValidateCheckException;

import java.nio.charset.StandardCharsets;


/**
 * 字符串比较处理程序
 *
 * @author Heaven96
 * @date 2021/10/19
 */
public class StringCompareHandler extends AbstractCompareHandler {
    /**
     * 处理请求
     * obj1 = obj2 返回 0 否则返回 -1
     *
     * @param obj1       obj1
     * @param obj2       obj2
     * @param ignoreCase 忽略大小写
     * @return boolean
     */
    @Override
    public int handle(Object obj1, Object obj2, boolean ignoreCase) {
        try {
            final String objStr1 = StrUtil.str(obj1, StandardCharsets.UTF_8).trim();
            final String objStr2 = StrUtil.str(obj2, StandardCharsets.UTF_8).trim();
            if (ignoreCase) {
                return objStr2.equalsIgnoreCase(objStr1) ? 0 : -1;
            }
            return objStr2.equals(objStr1) ? 0 : -1;
        } catch (Exception e) {
            e.printStackTrace();
            if (getNext() != null) {
                return getNext().handle(obj1, obj2, ignoreCase);
            } else {
                throw new H4nBeforeValidateCheckException(FCV_NO_MATCHED_HANDLER_ERR_MSG);
            }
        }
    }
}
