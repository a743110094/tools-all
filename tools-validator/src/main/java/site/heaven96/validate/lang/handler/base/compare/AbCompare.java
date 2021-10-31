package site.heaven96.validate.lang.handler.base.compare;

/**
 * 基础比较抽象类
 * @apiNote 目的是比较两个对象直接的大小/相等关系 不涉及单值和范围进行比较
 * @author Heaven96
 * @date 2021/10/19
 */
public abstract class AbCompare {
    public static final String FCV_NO_MATCHED_HANDLER_ERR_MSG = "\n===>没有匹配到Compare比较处理器";
    private AbCompare next;

    public AbCompare getNext() {
        return next;
    }

    public void setNext(AbCompare next) {
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
