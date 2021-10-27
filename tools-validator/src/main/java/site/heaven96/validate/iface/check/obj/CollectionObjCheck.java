package site.heaven96.validate.iface.check.obj;

import site.heaven96.validate.common.enums.ValueSetOrigin;

import java.util.Collection;

/**
 * 集合抽象对象检查
 * 核心思想是先找出合法值集（这个值集可能是给定的，可能是运算出来或外部取得的）
 * 然后遍历被检查的集合对象
 * 再调用简单对象比较的逻辑处理
 *
 * @author lgw3488
 * @date 2021/10/27
 */
public interface CollectionObjCheck<T extends Collection, VSO extends ValueSetOrigin> extends AbstractObjCheck {
}
