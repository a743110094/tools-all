package site.heaven96.validate.imp.check.obj;

import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.common.enums.ValueSetOrigin;
import site.heaven96.validate.iface.check.field.FieldCheck;
import site.heaven96.validate.iface.check.obj.CollectionObjCheck;

import javax.annotation.Resource;
import java.util.List;

/**
 * 列表对象检查
 *
 * @author lgw3488
 * @date 2021/10/27
 */
public class ListObjCheck<T extends List, VSO extends ValueSetOrigin> implements CollectionObjCheck {

    @Resource
    FieldCheck check;


    /**
     * 检查List对象中的值是否合法
     *
     * @param obj   被检查对象
     * @param value 合法值
     * @return boolean
     */
    @Override
    public boolean check(Object obj, String value) {
        List list = (List) obj;
        return !list.stream().anyMatch(item -> false == check.check(obj, Logic.BETWEEN_AND, value));
    }
}
