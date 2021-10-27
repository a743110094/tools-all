package site.heaven96.validate.imp.check.obj;

import cn.hutool.core.util.ClassUtil;
import site.heaven96.assertes.util.AssertUtil;
import site.heaven96.validate.common.enums.Logic;
import site.heaven96.validate.iface.check.field.FieldCheck;
import site.heaven96.validate.iface.check.obj.CollectionObjCheck;

import javax.annotation.Resource;

/**
 * 简单对象检查
 *
 * @author lgw3488
 * @date 2021/10/27
 */
public class SimpleObjCheck implements CollectionObjCheck {

    /**
     * OBJ类错误消息
     */
    private static final String OBJ_CLASS_ERR_MSG = "被检查对象必须是简单值类型";

    @Resource
    FieldCheck check;


    /**
     * 检查非集合对象中的值是否合法
     *
     * @param obj   被检查对象
     * @param value 合法值
     * @return boolean
     */
    @Override
    public boolean check(Object obj, String value) {
        boolean simpleValueType = ClassUtil.isSimpleValueType(obj.getClass());
        AssertUtil.isFalse(simpleValueType, OBJ_CLASS_ERR_MSG);
        return check.check(obj, Logic.BETWEEN_AND, value);
    }
}
