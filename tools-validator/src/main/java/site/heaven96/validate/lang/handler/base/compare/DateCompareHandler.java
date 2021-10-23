package site.heaven96.validate.lang.handler.base.compare;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import site.heaven96.validate.util.AssertUtil;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 日期比较处理程序
 *
 * @author Heaven96
 * @date 2021/10/19
 */
@Slf4j(topic = "==> H4n日期比较器 <==")
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
        if (site.heaven96.validate.util.DateUtil.isDate(obj1)){
            final Date date1 = (Date) obj1;
            //先看对象是不是Date
            boolean obj2IsDate = site.heaven96.validate.util.DateUtil.isDate(obj2);
            //再看是否可以转换为Date
            String obj2Str = StrUtil.str(obj2, StandardCharsets.UTF_8);
            if (!obj2IsDate){
                obj2IsDate = site.heaven96.validate.util.DateUtil.strCanBeCastToDate(obj2Str);
            }
            AssertUtil.isTrueThrowBeforeExp(obj2IsDate,"\n===> 值{}不能被转换为日期",obj2Str);
            return DateUtil.compare(date1,site.heaven96.validate.util.DateUtil.strCastToDate(obj2Str));
        }else {
            //不是日期类的比较 传递给责任链的下一环
            AssertUtil.isTrueThrowBeforeExp(ObjectUtil.isNotNull(getNext()),FCV_NO_MATCHED_HANDLER_ERR_MSG);
            return getNext().handle(obj1, obj2, ignoreCase);
        }
    }
}
