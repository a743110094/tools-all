package site.heaven96.validate.lang.handler.base.compare;

import cn.hutool.core.util.ObjectUtil;
import site.heaven96.assertes.common.exception.H4nBeforeValidateCheckException;

public class ObjectCompare extends AbCompare {
    /**
     * 处理请求
     * obj1 < obj2 返回 -1  obj1 = obj2 返回 0 obj1 > obj2返回 1
     *
     * @param obj1       obj1
     * @param obj2       obj2
     * @param ignoreCase 忽略大小写
     * @return boolean
     */
    @Override
    public int handle(Object obj1, Object obj2, boolean ignoreCase) {
        try {
            return ObjectUtil.equal(obj1, obj2) ? 0 : -1;
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
