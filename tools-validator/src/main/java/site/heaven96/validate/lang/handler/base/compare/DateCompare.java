package site.heaven96.validate.lang.handler.base.compare;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.util.H4nDateUtil;

import java.util.Date;

/**
 * 日期比较处理程序
 *
 * @author Heaven96
 * @date 2021/10/19
 */
@Slf4j(topic = "==> H4n日期比较器 <==")
public class DateCompare extends AbCompare {
    /**
     * 处理请求
     * 如果date1 < date2，返回数小于0，date1==date2返回0，date1 > date2 大于0
     *
     * @param obj1       obj1
     * @param obj2       obj2
     * @param ignoreCase 忽略大小写
     * @return boolean
     */
    @Override
    public int handle(Object obj1, Object obj2, boolean ignoreCase) {
        if (H4nDateUtil.isDate(obj1)){
            final Date date1 = (Date) obj1;
            final Date date2 = H4nDateUtil.toDate(obj2);
            AssertUtil.isTrueThrowBeforeExp(date2!=null,"\n===> 值{}不能被转换为日期",obj2);
            return DateUtil.compare(date1,date2);
        }else {
            //不是日期类的比较 传递给责任链的下一环
            AssertUtil.isTrueThrowBeforeExp(ObjectUtil.isNotNull(getNext()),FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj1, obj2, ignoreCase);
        }
    }
}
