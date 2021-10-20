package site.heaven96.validate.lang.handler.compare;

import cn.hutool.core.date.DateUtil;
import site.heaven96.validate.common.exception.H4nBeforeValidateCheckException;

import java.util.Date;

/**
 * 日期比较处理程序
 *
 * @author lgw3488
 * @date 2021/10/19
 */
public class DateCompareHandler extends AbstractCompareHandler {
    /**
     * 处理请求
     * obj1 = obj2 返回 0 否则 返回 -1
     *
     * @param obj1       obj1
     * @param obj2       obj2
     * @param ignoreCase 忽略大小写
     * @return boolean
     */
    @Override
    public int handle(Object obj1, Object obj2, boolean ignoreCase) {
        try {
            //final String objStr1 = StrUtil.str(obj1, StandardCharsets.UTF_8).trim();
            //final String objStr2 = StrUtil.str(obj2, StandardCharsets.UTF_8).trim();
            //TODO 不止Date
            final Date date1 = (Date) obj1;
            final Date date2 = (Date) obj2;
            return DateUtil.compare(date1, date2);
        } catch (Exception e) {
            if (getNext() != null) {
                return getNext().handle(obj1, obj2, ignoreCase);
            } else {
                throw new H4nBeforeValidateCheckException(FCV_NO_MATCHED_HANDLER_ERR_MSG);
            }
        }
    }
}
