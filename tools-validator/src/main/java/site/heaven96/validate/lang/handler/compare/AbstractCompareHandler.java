package site.heaven96.validate.lang.handler.compare;

/**
 * 比较抽象处理程序
 *
 * @author lgw3488
 * @date 2021/10/19
 */
public abstract class AbstractCompareHandler {
    public static final String FCV_NO_MATCHED_HANDLER_ERR_MSG = "\n===>没有匹配到Compare比较处理器";
    private AbstractCompareHandler next;

    public AbstractCompareHandler getNext() {
        return next;
    }

    public void setNext(AbstractCompareHandler next) {
        this.next = next;
    }

    /**
     * 处理请求
     * obj1 < obj2 返回 -1  obj1 = obj2 返回 0 obj1 > obj2返回 1
     *
     * @param obj1       obj1
     * @param obj2       obj2
     * @param ignoreCase 忽略大小写
     * @return boolean
     */
    public abstract int handle(final Object obj1, final Object obj2, final boolean ignoreCase);
}
