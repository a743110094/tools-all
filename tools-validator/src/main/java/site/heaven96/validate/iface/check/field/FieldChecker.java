package site.heaven96.validate.iface.check.field;


import site.heaven96.validate.common.enums.Logic;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 字段检查接口 (实现需要区分JDK8和JDK8以前) <br>
 * 需要提供 <li>被检查对象 <li>检查逻辑 <li>合法值（集） <br> 返回检查结果 <br>
 * 例如可检查包括但不仅限于以下的例子：
 * <li>1 大于 0 ? -> false
 * <li>2 介于 [0,2) ? -> false
 * <li>"3" 在 {"2","4"} 范围内 ? -> false
 * <li>"2020-01-01" 晚于 "2020-01-25" ? -> false
 * <li>"广州" 在 "SELECT 今天下雨的城市 FROM 数据库" -> 依据sql执行的结果判断
 * <li>"成都" 作为入参调用  boolean isSunny(String cityName); 方法后判断是否为晴天
 *
 * @author Heaven96
 * @date 2021/10/26
 */
public interface FieldChecker<A extends Annotation> {

    /**
     * 检查
     *
     * @param obj        被检查对象
     * @param logic      逻辑
     * @param ignoreCase 忽略大小写
     * @param legals     合法值（集）
     * @return boolean
     */
    boolean check(Object obj, Logic logic,boolean ignoreCase, Object legals);

}
